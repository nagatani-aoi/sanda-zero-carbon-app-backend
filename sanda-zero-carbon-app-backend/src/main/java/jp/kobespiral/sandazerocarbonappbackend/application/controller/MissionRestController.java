package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.DailyMissionDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.MissionValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.MissionService;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

/**
 * ユーザ側でのミッション画面表示をおこなうRESTコントローラ
 * 
 * @author kamae
 */
@RestController
@RequestMapping("/api")
public class MissionRestController {
    @Autowired
    MissionService missionService;

    /*------------------ Read ------------------- */
    /**
     * すべてのミッションを取得する
     * @return ミッションDTOのリスト
     */
    @GetMapping("/mission")
    @CrossOrigin("http://localhost:5173")
    Response<List<MissionDto>> getAllMission(){
        try{
            return ResponseCreator.succeed(missionService.getAllMission());
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.MISSION_DOES_NOT_EXIST,new MissionValidationException(MISSION_DOES_NOT_EXIST,"get all mission", String.format("mission does not exist")),null);
        }
    }

    /**
     * ユーザの持つ未達成のデイリーミッションを取得する
     * @return デイリーミッションDTOのリスト
     */
    @GetMapping("/daily-mission/{userId}")
    @CrossOrigin("http://localhost:5173")
    Response<List<DailyMissionDto>> getAllDailyMission(@PathVariable String userId){
        return ResponseCreator.succeed(missionService.getDailyMission(userId));
    }
    
}
