package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.DailyMissionDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.MissionService;

@RestController
@RequestMapping("/api")
public class MissionRestController {
    @Autowired
    MissionService missionService;

    
    @GetMapping("/mission")
    Response<List<MissionDto>> getAllMission(){
        return ResponseCreator.succeed(missionService.getAllMission());
    }

    @GetMapping("/daily-mission/{userId}")
    Response<List<DailyMissionDto>> getAllDailyMission(@PathVariable Long userId){
        return ResponseCreator.succeed(missionService.getDailyMission(userId));
    }
    

}
