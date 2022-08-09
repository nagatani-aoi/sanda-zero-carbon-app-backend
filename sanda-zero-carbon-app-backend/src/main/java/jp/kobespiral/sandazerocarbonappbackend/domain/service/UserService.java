package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.UserValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Achievement;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Difficulty;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.UserDailyStatus;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.AchievementRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.UserDailyStatusRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.UserRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.UserErrorCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author sato
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AchievementRepository achievementRepository;
    @Autowired
    UserDailyStatusRepository userDailyStatusRepository;
    @Autowired
    MissionRepository missionRepository;

    /**
     * ユーザ作成
     * @param form ユーザフォーム
     * @return リポジトリにセーブ
     */
    public User createUser(UserForm form){
        User user = form.toEntity();
        return userRepository.save(user);
    }
    /**
     * ユーザを取得
     * @param userId ユーザID
     * @return ユーザ
     */
    public User getUser(Long userId) {
        //微妙
        User user = userRepository.findById(userId).orElseThrow(()->new UserValidationException(USER_DOES_NOT_EXIST,"Not exist the user", String.format("Try to get userId : %d",userId)));
        return user;
    }

    /**
     * ユーザdtoを取得。メイン画面構成時に使用
     * @param userId
     * @return ユーザdto
     */
    public UserDto getUserDto(Long userId) {
        //微妙
        User user = userRepository.findById(userId).orElseThrow(()->new UserValidationException(USER_DOES_NOT_EXIST,"Not get the userDto", String.format("Try to get userDto. userId : %d",userId)));
        return UserDto.build(user);
    }
    
    /**
     * ユーザデイリーステータスの更新
     * @param userId ユーザID
     * @param achievementId 達成ID
     * @return ユーザデイリーステータス
     */

    public UserDailyStatus renewUserDailyStatus(Long userId,Long achievementId){
        Achievement achievement = achievementRepository.findById(achievementId).orElseThrow(()->new UserValidationException(USER_DOES_NOT_EXIST,"create the user", String.format("crate %d",userId)));
        //日付タイプの変更
        LocalDateTime localDateTime = achievement.getAchievedAt();
        LocalDate localDate = localDateTime.toLocalDate();
        //その日のデイリーステータスが存在しなければ、新たに作成
        if(!userDailyStatusRepository.existsByUserIdAndDate(userId, localDate)){
            UserDailyStatus tempUserDailyStatus = new UserDailyStatus(null, userId, localDate, 0, 0,0, false,false,false);
            userDailyStatusRepository.save(tempUserDailyStatus);
        }
        Mission mission = missionRepository.findById(achievement.getMissionId()).orElseThrow(()->new UserValidationException(USER_DOES_NOT_EXIST,"create the user", String.format("crate %d",userId)));

        UserDailyStatus userDailyStatus = userDailyStatusRepository.findByUserIdAndDate(userId, localDate);
        
        //ユーザのトータルポイントの更新
        User user = getUser(userId);
        user.setTotalPoint(user.getTotalPoint() + achievement.getGetPoint());
        userRepository.save(user);
        //デイリーステータスの更新
        userDailyStatus.setTotalPoint(userDailyStatus.getTotalPoint() + achievement.getGetPoint());
        userDailyStatus.setTotalCO2Reduction(userDailyStatus.getTotalCO2Reduction() + mission.getCO2Reduction());
        userDailyStatus.setTotalCostReduction(userDailyStatus.getTotalCostReduction() + mission.getCostReduction());
        //もしデイリーミッションならばフラグ更新
        if(achievement.getIsDailyMission()){
            if(mission.getDifficulty() == Difficulty.easy){
                userDailyStatus.setEasyMissionCompleted(true);
            }
            else if(mission.getDifficulty() == Difficulty.normal){
                userDailyStatus.setNormalMissionCompleted(true);;
            }
            else if(mission.getDifficulty() == Difficulty.easy){
                userDailyStatus.setHardMissionCompleted(true);
            }
        }
        userDailyStatusRepository.save(userDailyStatus);

        return userDailyStatus;
    }

    /**
     * ユーザデイリーステータスを取得
     * @param userId
     * @return
     */
    public UserDailyStatus getUserDailyStatus(Long userId) {
        LocalDate localDate = LocalDate.now();
        UserDailyStatus userDailyStatus = userDailyStatusRepository.findByUserIdAndDate(userId,localDate);
        //その日のユーザデイリーステータスがなかったら作成
        if(userDailyStatus == null){
            UserDailyStatus tempDailyStatus = new UserDailyStatus(null, userId, localDate, 0, 0,0, false,false,false);
            userDailyStatusRepository.save(tempDailyStatus);
            userDailyStatus = tempDailyStatus;
        }
        return userDailyStatus;
    }
    
}
