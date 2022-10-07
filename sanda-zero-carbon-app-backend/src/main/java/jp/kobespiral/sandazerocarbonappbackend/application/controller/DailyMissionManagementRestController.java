package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.DailyMissionDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.MissionValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.MissionManagementService;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

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
    @CrossOrigin("http://localhost:5173")
    Response<List<DailyMissionDto>> selectDailyMission(){
        try{
            return ResponseCreator.succeed(missionManagementService.selectDailyMissions());
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.MISSION_DOES_NOT_EXIST,new MissionValidationException(MISSION_DOES_NOT_EXIST,"select daily mission", String.format("mission does not exist")),null);
        }
    }
}
