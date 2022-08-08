package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.AchievementDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionAchieveForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.AchievementService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 達成のRESTController
 *
 * @author ing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AchievementRestController {
    /** 達成のサービス */
    private final AchievementService achievementService;

    /* -------------------- Create -------------------- */
    @PostMapping("/article/achive/")
    public Response<Boolean> achiveMission(MissionAchieveForm form) {
        return null;
    }

    /* -------------------- Read -------------------- */

    /**
     * 指定したユーザ，週の1週間分の達成リストを取得
     *
     * @param userId     ユーザーID
     * @param dateString String型の日付
     * @return ラップされた達成Dtoのリスト
     */
    @GetMapping("/achivement/weekly")
    public Response<List<AchievementDto>> getWeeklyAchievements(@RequestParam("userId") Long userId,
            @RequestParam("date") String dateString) {
        return ResponseCreator.succeed(achievementService.getAchivement(userId, dateString)); // 達成リストを取得
    }

}
