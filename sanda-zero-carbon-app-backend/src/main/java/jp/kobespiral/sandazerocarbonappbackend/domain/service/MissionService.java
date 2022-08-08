package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionDto;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.DailyMission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.DailyMissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TagRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.UserDailyStatusRepository;

/**
 * 
 */
@Service
public class MissionService {
    @Autowired
    DailyMissionRepository dailyMissionRepository;
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    UserDailyStatusRepository userDailyStatusRepository;
    @Autowired
    TagRepository tagRepository;

    public list<AchievementDto> getDailyMissionProgress(Long userId){

    }
    public List<DailyMissionDto> getDailyMission(Long userId){
        List<DailyMission> dailyMissionList = new 
        return 
    }

    public MissionDto getMission(Long missionId){
        Mission mission = missionRepository.findById(missionId).orElseThrow(IllegalArgumentException::new);
        Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(IllegalArgumentException::new);
        return MissionDto.build(mission, tag);
    }
    
    public List<MissionDto> getAllMission(){
        List<Mission> missionList = missionRepository.findAll();
        List<MissionDto> missionDtoList = new ArrayList<MissionDto>();
        for (Mission list : missionList) {
            Tag tag = tagRepository.findById(list.getTagId()).orElseThrow(IllegalArgumentException::new);
            missionDtoList.add(MissionDto.build(list, tag));
        }
        return missionDtoList;
    }

}
