package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionDto;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.MissionManagementService;

/**
 * 管理者側でミッションへのCRUDを行うRESTコントローラ
 * 
 * @author kamae
 */
@RestController
@RequestMapping("/api")
public class MissionManagementRestController {
    @Autowired
    MissionManagementService missionManagementService;
    
    /*---------------------- Create ---------------------------*/
    /**
     * ミッションの作成を行う
     * @param MissionForm
     * @return 作成したミッションのDTO
     */
    @PostMapping("/sanda-admin/mission")
    Response<MissionDto> createMission(@RequestBody MissionForm form){
        return ResponseCreator.succeed(missionManagementService.createMission(form));
    }


    /*---------------------- Read -------------------------- */
    /**
     * 指定したIDのミッションを取得する
     * @return 指定したIDのミッションDTO
     */
    @GetMapping("/sanda-admin/mission/{missionId}")
    Response<MissionDto> getMission(@PathVariable Long missionId){
        return ResponseCreator.succeed(missionManagementService.getMission(missionId));
    }

    /**
     * すべてのミッションを取得する
     * @return すべてのミッションのDTOリスト
     */
    @GetMapping("/sanda-admin/mission")
    Response<List<MissionDto>> getAllMission(){
        return ResponseCreator.succeed(missionManagementService.getAllMissions());
    }


    /*----------------------- Update --------------------------- */
    /**
     * 指定したIDのミッション内容の更新
     * @param MissionForm
     * @return 更新したミッションのDTO
     */
    @PutMapping("/sanda-admin/mission/{missionId}")
    Response<MissionDto> updateMission(@PathVariable Long missionId, @RequestBody MissionForm form){
        return ResponseCreator.succeed(missionManagementService.updateMission(missionId, form));
    }


    /*-----------------------Delete------------------------- */
    /**
     * 指定したIDのミッションを削除する
     * @param missionId
     * @return boolean
     */
    @DeleteMapping("/sanda-admin/mission")
    Response<Boolean> deleteMission(@RequestParam("missionId") Long missionId){
        return ResponseCreator.succeed(missionManagementService.deleteMission(missionId));
    }
}
