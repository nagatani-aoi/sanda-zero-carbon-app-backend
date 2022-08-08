package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.AchievementDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionAchieveForm;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Achievement;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;

/**
 * 達成のサービス
 *
 * @author ing
 */
@Service
@RequiredArgsConstructor
public class AchivementService {
    /** 達成のリポジトリ */
    private final AchievementRepository achievementRepository;

    /** ミッションのサービス */
    private final MissionService missionService;

    /**
     * 達成Dtoのリストを取得
     *
     * @param userId ユーザID
     * @param date   日時
     * @return 達成Dtoのリスト
     */
    public List<AchievementDto> getAchivement(Long userId, LocalDateTime date) {
        LocalDateTime monday = date.with(DayOfWeek.MONDAY); // その週の月曜日を取得
        LocalDateTime sunday = date.with(DayOfWeek.SUNDAY); // その週の日曜日を取得
        // 参考 :
        // https://www.bing.com/ck/a?!&&p=54e655132aae1b06JmltdHM9MTY1OTkyMTY4NyZpZ3VpZD1kZDI1OTBkYS1jZDY3LTRiYmYtYTVlZi1kZGU2YmE0NGQzNzYmaW5zaWQ9NTE5Mg&ptn=3&hsh=3&fclid=6c93149d-16b8-11ed-a9cd-9b2466a350dc&u=a1aHR0cHM6Ly9xaWl0YS5jb20va2F6b2tjbGlseS9pdGVtcy8zMzY0ZTI1YjYxMjQ4N2RkYWMwNQ&ntb=1

        List<Achievement> achievements = achievementRepository.findByUserIdAndAchievedAtBetween(userId, monday, sunday); // その週の達成リストを取得

        ArrayList<AchievementDto> achievementDtos = new ArrayList<AchievementDto>();

        for (Achievement achievement : achievements) {
            MissionDto missionDto = missionService.getMission(achievement.getMissionId()); // 達成ミッションに紐づいたミッションを取得

            achievementDtos.add(AchievementDto.build(achievement, missionDto)); // 達成のDtoを達成のDtoリストに追加
        }

        return achievementDtos;
    }

    public void achieveMission(MissionAchieveForm form) {

    }

}
