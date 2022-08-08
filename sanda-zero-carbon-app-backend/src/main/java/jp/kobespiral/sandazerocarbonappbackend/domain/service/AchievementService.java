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

    /** ルール */
    private final Rule rule;

    /* -------------------- Create -------------------- */

    // public void achiveMission(MissionAchieveForm form) {
    // UserDailyStatus userDailyStatus =
    // userService.getUserDailyStatus(form.getUserId()); //
    // ユーザIDからユーザーデイリーステータスDtoを取得

    // MissionDto missionDto = missionService.getMission(form.getMissionId()); //
    // 達成したミッションIDからミッションDtoを取得

    // int getPoint; // 取得予定ポイント

    // if(form.getIsDailyMission()){ // デイリーミッションならば
    // getPoint = missionDto.getPoint(); // デイリーミッションは最小単位のポイントのみ
    // }else{ // デイリーミッションでないならば
    // if(missionDto.getMissionType() == MissionType.TimeType){ // ミッションのタイプが時間制ならば
    // getPoint = missionDto.getPoint() * form.getHour(); // デイリーミッションは最小単位のポイントのみ
    // }else{
    // getPoint = missionDto.getPoint();
    // }

    // }

    // if (dto.getTotalPoint() > Rule.innerRule.maxMissionPoint) {
    // getPoint = 0;
    // }else if(dto.getTotalPoint() + ){

    // }
    // }

    /* -------------------- Read -------------------- */

    /**
     * 達成Dtoのリストを取得
     *
     * @param userId ユーザID
     * @param date   日時
     * @return 達成Dtoのリスト
     */
    public List<AchievementDto> getAchivement(Long userId, String dateString) {
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
}