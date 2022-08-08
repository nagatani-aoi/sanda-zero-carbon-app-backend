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
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionDto;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.MissionManagementService;

@RestController
@RequestMapping("/api")
public class MissionManagementRestController {
    
    @Autowired
    MissionManagementService missionManagementService;
    
    @PostMapping("/sanda-admin/mission")
    Response<MissionDto> createMission(@RequestBody MissionForm form){
        return ResponseCreator.succeed(missionManagementService.createMission(form));
    }
    
    @GetMapping("/sanda-admin/mission/{missionId}")
    Response<MissionDto> getMission(@PathVariable Long missionId){
        return ResponseCreator.succeed(missionManagementService.getMission(missionId));
    }

    @GetMapping("/sanda-admin/mission")
    Response<List<MissionDto>> getAllMission(){
        return ResponseCreator.succeed(missionManagementService.getAllMissions());
    }

    @PutMapping("/sanda-admin/mission/{missionId}")
    Response<MissionDto> updateMission(@PathVariable Long missionId, @RequestBody MissionForm form){
        return ResponseCreator.succeed(missionManagementService.updateMission(missionId, form));
    }

    @DeleteMapping("/sanda-admin/mission")
    Response<Boolean> deleteMission(Long missionId){
        return ResponseCreator.succeed(missionManagementService.deleteMission(missionId));
    }
}
