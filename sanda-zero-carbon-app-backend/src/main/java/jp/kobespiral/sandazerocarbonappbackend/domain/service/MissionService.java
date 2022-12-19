package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.DailyMissionDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.MissionValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.TagValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.DailyMission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.UserDailyStatus;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.DailyMissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TagRepository;
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
    // @Autowired
    // UserDailyStatusRepository userDailyStatusRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    UserService userService;

    /*--------------------------------Read------------------------------- */
    /**
     * ユーザID、デイリーミッション、デイリーミッションのDTOリストを入力し、
     * そのユーザが引数で指定したデイリーミッションをまだ達成していなければ、
     * DTOリストに追加する
     * 
     * @param userId
     * @param dailyMission
     * @param dailyMissionDtoList
     * @return デイリーミッションDTOのリスト
     */
    public boolean getDailyMissionProgress(String userId, Mission mission, DailyMission dailyMission) {
        UserDailyStatus userDailyStatus = userService.getUserDailyStatus(userId);
        switch (mission.getDifficulty()) {
            case easy:
                return !(userDailyStatus.getEasyMissionCompleted());
            case normal:
                return !(userDailyStatus.getNormalMissionCompleted());
            case hard:
                return !(userDailyStatus.getHardMissionCompleted());
        }
        return false;
    }

    /**
     * ユーザが未達成のデイリーミッションをDTOリストで出力する
     * 
     * @param userId
     * @return デイリーミッションDTOのリスト
     */
    public List<DailyMissionDto> getDailyMission(String userId) {
        List<DailyMission> dailyMissionList = dailyMissionRepository.findByDateGreaterThanEqual(LocalDate.now());
        List<DailyMissionDto> dailyMissionDtoList = new ArrayList<DailyMissionDto>();

        for (DailyMission list : dailyMissionList) {

            Mission mission = missionRepository.findById(list.getMissionId())
                    .orElseThrow(() -> new MissionValidationException(NO_SUCH_MISSION_EXISTS, "get dailyMission",
                            String.format("mission (Id:%d) does not exist", list.getMissionId())));
            if (getDailyMissionProgress(userId, mission, list)) {
                Tag tag = tagRepository.findById(mission.getTagId())
                        .orElseThrow(() -> new TagValidationException(NO_SUCH_TAG_EXISTS,
                                "get tag in getDailyMission",
                                String.format("tag (Id:%d) does not exist", mission.getTagId())));
                dailyMissionDtoList.add(DailyMissionDto.build(mission, list, tag));
            }
        }
        return dailyMissionDtoList;
    }

    /**
     * 指定したIDのミッションをDTO形式で取得する
     * 
     * @param missionId
     * @return ミッションDTO
     */
    public MissionDto getMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionValidationException(NO_SUCH_MISSION_EXISTS, "get mission",
                        String.format("mission (Id:%d) does not exist", missionId)));
        Tag tag = tagRepository.findById(mission.getTagId())
                .orElseThrow(() -> new TagValidationException(NO_SUCH_TAG_EXISTS,
                        "get tag in get mission", String.format("tag (Id:%d) does not exist", mission.getTagId())));
        return MissionDto.build(mission, tag);
    }

    /**
     * すべてのミッションをDTO形式のリストで出力する
     * 
     * @return ミッションDTOのリスト
     */
    public List<MissionDto> getAllMission() {
        List<Mission> missionList = missionRepository.findAll();
        List<MissionDto> missionDtoList = new ArrayList<MissionDto>();
        for (Mission list : missionList) {
            Tag tag = tagRepository.findById(list.getTagId())
                    .orElseThrow(() -> new TagValidationException(NO_SUCH_TAG_EXISTS,
                            "get tag in get AllMission",
                            String.format("tag (Id:%d) does not exist", list.getTagId())));
            missionDtoList.add(MissionDto.build(list, tag));
        }
        return missionDtoList;
    }

}
