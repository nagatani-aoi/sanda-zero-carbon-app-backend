package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jp.kobespiral.sandazerocarbonappbackend.domain.service.MissionManagementService;

@Controller
public class MissionManagementRestController {
    @Autowired
    MissionManagementService missionManageService;
    /* 
    @PostMapping("/sanda-admin/mission/")
    Response<Boolean> createMission(MissionFrom form){
        missionManageService.createMission(form);
    }
    */
}
