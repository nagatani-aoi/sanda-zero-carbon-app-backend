package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionForm;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.DailyMission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Difficulty;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.DailyMissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TagRepository;

/**
 * 管理者側でミッション・デイリーミッションに関するCRUDを行うサービス
 * 
 * @author kamae
 */
@Service
public class MissionManagementService {
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    DailyMissionRepository dailyMissionRepository;
    @Autowired
    TagRepository tagRepository;

    /*-------------------Create------------------- */
    /**
     * ミッションの作成を行う
     * @param form
     * @return 作成したミッションのDTO
     */
    public MissionDto createMission(MissionForm form){
        Mission mission = form.toEntity();
        missionRepository.save(mission);
        Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(IllegalArgumentException::new);
        return MissionDto.build(mission, tag);
    }

    /**
     * デイリーミッションの選定を行う
     * @return bool変数
     */
    public boolean selectDailyMissions(){
        for(Difficulty difficulty:Difficulty.values()){
            DailyMission dailyMission = new DailyMission();
            List<Mission> missionList = missionRepository.findByDifficulty(difficulty);
            Random rand = new Random();
            int index = rand.nextInt(missionList.size());
            dailyMission.setMissionId(missionList.get(index).getMissionId());
            dailyMission.setDate(LocalDate.now());
            dailyMissionRepository.save(dailyMission);
        }
        return true;
    }


    /*--------------------Read--------------------- */
    /**
     * すべてのミッションを取得する
     * @return すべてのミッションのDTOリスト
     */
    public List<MissionDto> getAllMissions(){
        List<Mission> missionList = missionRepository.findAll();
        List<MissionDto> missionDtoList = new ArrayList<MissionDto>();
        for (Mission list : missionList) {
            Tag tag = tagRepository.findById(list.getTagId()).orElseThrow(IllegalArgumentException::new);
            missionDtoList.add(MissionDto.build(list, tag));
        }
        return missionDtoList;
    }

    /**
     * 指定したIDのミッションを取得する
     * @param missionId
     * @return 指定したミッションのDTO
     */
    public MissionDto getMission(Long missionId){
        Mission mission = missionRepository.findById(missionId).orElseThrow(IllegalArgumentException::new);
        Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(IllegalArgumentException::new);
        return MissionDto.build(mission, tag);
    }


    /*--------------------Update-------------------------- */
    /**
     * IDで指定したミッションの更新を行う
     * @param missionId
     * @param form
     * @return 更新したミッションのDTO
     */
    public MissionDto updateMission(Long missionId, MissionForm form){
        Mission mission = missionRepository.findById(missionId).orElseThrow(IllegalArgumentException::new);
        mission.setPoint(form.getPoint());
        mission.setTitle(form.getTitle());
        mission.setDescription(form.getDescription());
        mission.setCo2Reduction(form.getCO2Reduction());
        mission.setCostReduction(form.getCostReduction());
        mission.setDifficulty(form.getDifficulty());
        mission.setMissionType(form.getMissionType());
        mission.setTagId(form.getTagId());
        Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(IllegalArgumentException::new);
        return MissionDto.build(missionRepository.save(mission), tag);
    }
    

    /*--------------------------Delete----------------------- */
    /**
     * IDで指定したミッションの削除を行う
     * @param missionId
     * @return bool変数
     */
    public boolean deleteMission(Long missionId){
        missionRepository.deleteById(missionId);
        return true;
    }
    

    /*-----------------------Other----------------------- */
    /**
     * 引数に指定したキーワードをタイトルに含んだミッションを探索する
     * @param keyword
     * @return ミッションDTOのリスト
     */
    public List<MissionDto> searchMissionByKeyword(String keyword){
        List<Mission> missionList = missionRepository.findByTitleContaining(keyword);
        List<MissionDto> missionDtoList = new ArrayList<MissionDto>();
        for (Mission list : missionList) {
            Tag tag = tagRepository.findById(list.getTagId()).orElseThrow(IllegalArgumentException::new);
            missionDtoList.add(MissionDto.build(list, tag));
        }
        return missionDtoList;
    }

    /**
     * 引数に指定したタグを持ったミッションを探索する
     * @param tagId
     * @return ミッションDTOのリスト
     */
    public List<MissionDto> searchMissionByTag(Long tagId){
        List<Mission> missionList = missionRepository.findByTagId(tagId);
        Tag tag = tagRepository.findById(tagId).orElseThrow(IllegalArgumentException::new);
        List<MissionDto> missionDtoList = new ArrayList<MissionDto>();
        for (Mission list : missionList) {
            missionDtoList.add(MissionDto.build(list, tag));
        }
        return missionDtoList;
    }

}

