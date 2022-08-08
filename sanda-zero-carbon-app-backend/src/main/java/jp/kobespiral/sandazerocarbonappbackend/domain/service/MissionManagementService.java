package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionForm;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TagRepository;

public class MissionManagementService {
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    TagRepository tagRepository;

    public MissionDto createMission(MissionForm form){
        Mission mission = form.toEntity();
        missionRepository.save(mission);
        Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(IllegalArgumentException::new);
        return MissionDto.build(mission, tag);
    }

    public List<MissionDto> getAllMissions(){
        List<Mission> missionList = missionRepository.findAll();
        List<MissionDto> missionDtoList = new ArrayList<MissionDto>();
        for (Mission list : missionList) {
            Tag tag = tagRepository.findById(list.getTagId()).orElseThrow(IllegalArgumentException::new);
            missionDtoList.add(MissionDto.build(list, tag));
        }
        return missionDtoList;
    }

    public MissionDto getMission(Long missionId){
        Mission mission = missionRepository.findById(missionId).orElseThrow(IllegalArgumentException::new);
        Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(IllegalArgumentException::new);
        return MissionDto.build(mission, tag);
    }

    public MissionDto updateMission(Long missionId, MissionForm form){
        Mission mission = missionRepository.findById(missionId).orElseThrow(IllegalArgumentException::new);
        mission.setPoint(form.getPoint());
        mission.setTitle(form.getTitle());
        mission.setDescription(form.getDescription());
        mission.setCO2Reduction(form.getCO2Reduction());
        mission.setCostReduction(form.getCostReduction());
        mission.setDifficulty(form.getDifficulty());
        mission.setMissionType(form.getMissionType());
        mission.setTagId(form.getTagId());
        Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(IllegalArgumentException::new);
        return MissionDto.build(mission, tag);
    }
    
    public boolean deleteMission(Long missionId){
        missionRepository.deleteById(missionId);
        return true;
    }
    
    public List<MissionDto> searchMissionByKeyword(String keyword){
        List<Mission> missionList = missionRepository.findByTitleContaining(keyword);
        List<MissionDto> missionDtoList = new ArrayList<MissionDto>();
        for (Mission list : missionList) {
            Tag tag = tagRepository.findById(list.getTagId()).orElseThrow(IllegalArgumentException::new);
            missionDtoList.add(MissionDto.build(list, tag));
        }
        return missionDtoList;
    }

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

