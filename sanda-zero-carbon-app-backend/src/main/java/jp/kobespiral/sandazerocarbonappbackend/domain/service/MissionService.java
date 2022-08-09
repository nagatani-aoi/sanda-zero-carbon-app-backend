package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.DailyMissionDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.MissionValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.DailyMission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.UserDailyStatus;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.DailyMissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TagRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.UserDailyStatusRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

/**
 * ユーザ側のミッション・デイリーミッションに関するサービス
 * 
 * @author kamae
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

    /*--------------------------------Read------------------------------- */
    /**
     * ユーザID、デイリーミッション、デイリーミッションのDTOリストを入力し、
     * そのユーザが引数で指定したデイリーミッションをまだ達成していなければ、
     * DTOリストに追加する
     * @param userId
     * @param dailyMission
     * @param dailyMissionDtoList
     * @return デイリーミッションDTOのリスト
     */
    public List<DailyMissionDto> getDailyMissionProgress(String userId, DailyMission dailyMission, List<DailyMissionDto> dailyMissionDtoList){
        Mission mission = missionRepository.findById(dailyMission.getMissionId()).orElseThrow(IllegalArgumentException::new);
        UserDailyStatus userDailyStatus = userDailyStatusRepository.findByUserIdAndDate(userId, LocalDate.now());
        switch (mission.getDifficulty()){
            case easy:
                if (userDailyStatus.getEasyMissionCompleted() == false) {
                    Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(()->new MissionValidationException(NO_TAG_CORRESPONDING_TO_THE_MISSION,"give tag to easy daily mission", String.format("give tag to %d",mission.getMissionId())));
                    dailyMissionDtoList.add(DailyMissionDto.build(mission, dailyMission, tag));
                }
            case normal:
                if (userDailyStatus.getNormalMissionCompleted() == false) {
                    Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(()->new MissionValidationException(NO_TAG_CORRESPONDING_TO_THE_MISSION,"give tag to normal daily mission", String.format("give tag to %d",mission.getMissionId())));
                    dailyMissionDtoList.add(DailyMissionDto.build(mission, dailyMission, tag));
                }
            case hard:
                if (userDailyStatus.getHardMissionCompleted() == false) {
                    Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(()->new MissionValidationException(NO_TAG_CORRESPONDING_TO_THE_MISSION,"give tag to hard daily mission", String.format("give tag to %d",mission.getMissionId())));
                    dailyMissionDtoList.add(DailyMissionDto.build(mission, dailyMission, tag));
                }
        }

        return dailyMissionDtoList;
    }

    /**
     * ユーザが未達成のデイリーミッションをDTOリストで出力する
     * @param userId
     * @return デイリーミッションDTOのリスト
     */
    public List<DailyMissionDto> getDailyMission(String userId){
        List<DailyMission> dailyMissionList = dailyMissionRepository.findByDateGreaterThanEqual(LocalDate.now());
        List<DailyMissionDto> dailyMissionDtoList = new ArrayList<DailyMissionDto>();

        for(DailyMission list: dailyMissionList){
            getDailyMissionProgress(userId, list, dailyMissionDtoList);
        }
        return dailyMissionDtoList;
    }

    /**
     * 指定したIDのミッションをDTO形式で取得する
     * @param missionId
     * @return ミッションDTO
     */
    public MissionDto getMission(Long missionId){
        Mission mission = missionRepository.findById(missionId).orElseThrow(()->new MissionValidationException(MISSION_DOES_NOT_EXIST,"create the mission", String.format("create %d",missionId)));
        Tag tag = tagRepository.findById(mission.getTagId()).orElseThrow(()->new MissionValidationException(NO_TAG_CORRESPONDING_TO_THE_MISSION,"give tag to the mission", String.format("give tag to %d",missionId)));
        return MissionDto.build(mission, tag);
    }
    
    /**
     * すべてのミッションをDTO形式のリストで出力する
     * @return ミッションDTOのリスト
     */
    public List<MissionDto> getAllMission(){
        List<Mission> missionList = missionRepository.findAll();
        List<MissionDto> missionDtoList = new ArrayList<MissionDto>();
        for (Mission list : missionList) {
            Tag tag = tagRepository.findById(list.getTagId()).orElseThrow(()->new MissionValidationException(NO_TAG_CORRESPONDING_TO_THE_MISSION,"give tag to the mission", String.format("give tag to %d",list.getMissionId())));
            missionDtoList.add(MissionDto.build(list, tag));
        }
        return missionDtoList;
    }

}
