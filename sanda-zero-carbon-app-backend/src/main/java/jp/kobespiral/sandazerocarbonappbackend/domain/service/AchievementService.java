package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.AchievementDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionAchieveForm;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.TotalParamDto;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Achievement;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.MissionType;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.UserDailyStatus;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.AchievementRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.utils.Rule;
import lombok.RequiredArgsConstructor;

/**
 * 達成のサービス
 *
 * @author ing
 */
@Service
@RequiredArgsConstructor
public class AchievementService {
    /** 達成のリポジトリ */
    private final AchievementRepository achievementRepository;

    /** ミッションのサービス */
    private final MissionService missionService;
    /** ユーザーのサービス */
    private final UserService userService;

    /* -------------------- Create -------------------- */

    /**
     * ミッションを達成
     *
     * @param form ミッション達成フォーム
     */
    public AchievementDto achieveMission(MissionAchieveForm form) {
        String userId = form.getUserId(); // ユーザーIDを固定
        Long missionId = form.getMissionId(); // ミッションIDを固定
        int hour = form.getHour(); // 時間を固定
        Boolean isDailyMission = form.getIsDailyMission(); // デイリーミッションフラグを固定

        UserDailyStatus userDailyStatus = userService.getUserDailyStatus(userId);
        // ユーザIDからユーザーデイリーステータスDtoを取得

        MissionDto missionDto = missionService.getMission(missionId);
        // 達成したミッションIDからミッションDtoを取得

        int getPoint; // 取得予定ポイント
        int getRealPoint; // 実際の取得ポイント

        if (isDailyMission) { // デイリーミッションならば
            getPoint = missionDto.getPoint() * 2; // デイリーミッションは最小単位のポイントのみ
        } else { // デイリーミッションでないならば
            if (missionDto.getMissionType() == MissionType.TimeType) { // ミッションのタイプが時間制ならば
                getPoint = missionDto.getPoint() * hour; // 最小単位のポイントに時間を乗算
            } else { // ミッションのタイプが時間制でないならば
                getPoint = missionDto.getPoint(); // 最小単位のポイントのみ
            }
        }

        if (userDailyStatus.getTotalPoint() > Rule.maxMissionPoint) { // ポイント取得上限に達していたら
            getRealPoint = 0; // ポイント0
        } else if (userDailyStatus.getTotalPoint() + getPoint > Rule.maxMissionPoint) { // 今回のポイント獲得分を足すとポイント取得上限に達するなら
            getRealPoint = Rule.maxMissionPoint - userDailyStatus.getTotalPoint(); // 上限までのポイント
        } else {
            getRealPoint = getPoint; // ポイント全て
        }

        Achievement achievement = form.toEntity(); // MissionAchieveFormからAchieveを作成

        // 必要な情報をセット
        achievement.setGetPoint(getRealPoint);
        achievement.setAchievedAt(LocalDateTime.now()); // 現在時間をセット

        Achievement createdAchievement = achievementRepository.save(achievement);

        userService.renewUserDailyStatus(userId, createdAchievement.getAchievementId()); // ユーザーデイリーステータスを更新

        return AchievementDto.build(createdAchievement, missionDto);
    }

    /* -------------------- Read -------------------- */

    /**
     * 達成Dtoのリストを取得
     *
     * @param userId ユーザID
     * @param date   日時
     * @return 達成Dtoのリスト
     */
    public List<AchievementDto> getAchivement(String userId, String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy/MM/dd")); // String型の日付をLocalDateに変換

        LocalDate monday = date.with(DayOfWeek.MONDAY); // その週の月曜日を取得
        LocalDate nextMonday = date.with(DayOfWeek.SUNDAY).plusDays(1); // その次週の日曜日を取得

        // 参考 :
        // https://www.bing.com/ck/a?!&&p=54e655132aae1b06JmltdHM9MTY1OTkyMTY4NyZpZ3VpZD1kZDI1OTBkYS1jZDY3LTRiYmYtYTVlZi1kZGU2YmE0NGQzNzYmaW5zaWQ9NTE5Mg&ptn=3&hsh=3&fclid=6c93149d-16b8-11ed-a9cd-9b2466a350dc&u=a1aHR0cHM6Ly9xaWl0YS5jb20va2F6b2tjbGlseS9pdGVtcy8zMzY0ZTI1YjYxMjQ4N2RkYWMwNQ&ntb=1

        LocalDateTime mondayStart = monday.atStartOfDay(); // その週の月曜日の始まりの日時をを取得
        LocalDateTime sundayEnd = nextMonday.atStartOfDay(); // その週の日曜日の終わりの日時をを取得

        // 参考
        // https://tech-parrot.com/java/java-convert-date-datetime-localdatetime-and-string/#19_String_to_LocalDate_Java

        List<Achievement> achievements = achievementRepository.findByUserIdAndAchievedAtBetween(userId, mondayStart,
                sundayEnd); // その週の達成リストを取得

        ArrayList<AchievementDto> achievementDtos = new ArrayList<AchievementDto>();

        for (Achievement achievement : achievements) {
            MissionDto missionDto = missionService.getMission(achievement.getMissionId()); // 達成ミッションに紐づいたミッションを取得

            achievementDtos.add(AchievementDto.build(achievement, missionDto)); // 達成のDtoを達成のDtoリストに追加
        }

        return achievementDtos;
    }

    /**
     * 特定ユーザーの累計のパラメータを取得
     *
     * @param userId ユーザーID
     * @return 累計のパラメータDto
     */
    public TotalParamDto getTotalParam(String userId) {
        TotalParamDto dto = new TotalParamDto(); // 累計のパラメータDto

        List<Achievement> achievements = achievementRepository.findByUserId(userId); // ユーザーの達成リストを取得

        Double totalCO2Reduction = 0d; // 累計CO2削減量
        Double totalCostReduction = 0d; // 累計節約金額

        for (Achievement achievement : achievements) { // ユーザーの達成リストごとに
            MissionDto missionDto = missionService.getMission(achievement.getMissionId()); // 達成ミッションに紐づいたミッションを取得

            if (missionDto.getMissionType() == MissionType.DoType) { // ミッションタイプが時間制でないなら
                totalCO2Reduction += missionDto.getCO2Reduction(); // 最小単位のCO2削減量を加算
                totalCostReduction += missionDto.getCostReduction(); // 最小単位の節約金額を加算
            } else {
                totalCO2Reduction += missionDto.getCO2Reduction() * achievement.getHour(); // 最小単位のCO2削減量に時間を乗算したものを加算
                totalCostReduction += missionDto.getCostReduction() * achievement.getHour(); // 最小単位の節約金額に時間を乗算したものを加算
            }
        }

        // 累計のパラメータDtoに情報をセット
        dto.setUserId(userId);
        dto.setTotalCO2Reduction(totalCO2Reduction);
        dto.setTotalCostReduction(totalCostReduction);
        dto.setAchievementNum(achievements.size());

        return dto;
    }
}