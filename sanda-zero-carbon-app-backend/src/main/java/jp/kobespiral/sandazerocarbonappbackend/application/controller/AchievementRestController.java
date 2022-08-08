package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.AchievementDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionAchieveForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.AchievementService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
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

    @GetMapping("/achivement/weekly")
    public Response<List<AchievementDto>> getMethodName(@RequestParam("uid") String uid,
            @RequestParam("date") LocalDate date) {
        return new SomeData();
    }

}
