package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jp.kobespiral.sandazerocarbonappbackend.domain.service.MissionService;

@Controller
public class MissionRestController {
    @Autowired
    MissionService missionService;

    /*
    @GetMapping("/mission")
    Response<List<MissionDto>> getAllMission(){

    }
    @GetMapping("/daily-mission/{userId}")
    Response<List<DailyMissionDto>> getAllDailyMission(){

    }
    */

}
