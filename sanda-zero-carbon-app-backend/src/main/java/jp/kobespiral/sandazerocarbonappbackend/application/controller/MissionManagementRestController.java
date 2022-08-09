package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode;
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
    @CrossOrigin("https://localhost:5173")
    Response<MissionDto> createMission(@Validated @RequestBody MissionForm form){
        return ResponseCreator.succeed(missionManagementService.createMission(form));
    }


    /*---------------------- Read -------------------------- */
    /**
     * 指定したIDのミッションを取得する
     * @return 指定したIDのミッションDTO
     */
    @GetMapping("/sanda-admin/mission/{missionId}")
    @CrossOrigin("https://localhost:5173")
    Response<MissionDto> getMission(@PathVariable Long missionId){
        return ResponseCreator.succeed(missionManagementService.getMission(missionId));
    }

    /**
     * すべてのミッションを取得する
     * @return すべてのミッションのDTOリスト
     */
    @GetMapping("/sanda-admin/mission")
    @CrossOrigin("https://localhost:5173")
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
    @CrossOrigin("https://localhost:5173")
    Response<MissionDto> updateMission(@PathVariable Long missionId, @Validated @RequestBody MissionForm form){
        return ResponseCreator.succeed(missionManagementService.updateMission(missionId, form));
    }


    /*-----------------------Delete------------------------- */
    /**
     * 指定したIDのミッションを削除する
     * @param missionId
     * @return boolean
     */
    @DeleteMapping("/sanda-admin/mission")
    @CrossOrigin("https://localhost:5173")
    Response<Boolean> deleteMission(@RequestParam("missionId") Long missionId){
        try{
            return ResponseCreator.succeed(missionManagementService.deleteMission(missionId));
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.MISSION_DOES_NOT_EXIST,e,null);
        }
    }


    /*--------------------Other-----------------------*/
    /**
     * 引数に指定したキーワードをタイトルに含んだミッションを探索する
     * @param keyword
     * @return ミッションDTOのリスト
     */
    @PostMapping("/sanda-admin/search/keyword")
    @CrossOrigin("https://localhost:5173")
    Response<List<MissionDto>> searchMissionByKeyword(@RequestParam("keyword") String keyword){
        return ResponseCreator.succeed(missionManagementService.searchMissionByKeyword(keyword));
    }

    /**
     * 引数に指定したタグを持ったミッションを探索する
     * @param tagId
     * @return ミッションDTOのリスト
     */
    @PostMapping("/sanda-admin/search/tag")
    @CrossOrigin("https://localhost:5173")
    Response<List<MissionDto>> searchMissionByTag(@RequestParam("tag") Long tagId){
        return ResponseCreator.succeed(missionManagementService.searchMissionByTag(tagId));
    }
}
