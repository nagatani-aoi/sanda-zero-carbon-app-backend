package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.DailyMissionDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.MissionManagementService;

@RestController
@RequestMapping("/api")
public class DailyMissionManagementRestController {
    @Autowired
    MissionManagementService missionManagementService;

    /*--------------------------Create------------------------- */
    /**
     * 毎日0時に今日のデイリーミッションの選定を行う
     * @return boolean
     */
    @PostMapping("/sanda-admin/daily-mission")
    Response<List<DailyMissionDto>> selectDailyMission(){
        return ResponseCreator.succeed(missionManagementService.selectDailyMissions());
    }
}
