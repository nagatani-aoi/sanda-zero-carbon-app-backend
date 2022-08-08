package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;

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
        User user = userRepository.findById(userId).orElseThrow(()->new UserValidationException(USER_DOES_NOT_EXIST,"create the user", String.format("crate %d",userId)));
        return user;
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
        }
        UserDailyStatus userDailyStatus = userDailyStatusRepository.findByUserIdAndDate(userId, localDate);
        
        
        //デイリーステータスの更新
        userDailyStatus.setTotalPoint(userDailyStatus.getTotalPoint() + achievement.getGetPoint());
        userDailyStatus.setTotalCO2Reduction(userDailyStatus.getTotalCostReduction() + achievement.getGetCO2Reduction());
        userDailyStatus.setTotalCostReduction(userDailyStatus.getTotalCostReduction() + achievement.getGetCostReduction());
        //もしデイリーミッションならばフラグ更新
        if(achievement.getIsDailyMission()){
            Mission mission = missionRepository.findById(achievement.getMissionId()).orElseThrow(()->new UserValidationException(USER_DOES_NOT_EXIST,"create the user", String.format("crate %d",userId)));
            if(mission.getDifficulty() == Difficulty.easy){
                userDailyStatus.setEasyMissionCompleted(true);
            }
            else if(mission.getDifficulty() == Difficulty.normal){
                userDailyStatus.setNomalMissionCompleted(true);;
            }
            else if(mission.getDifficulty() == Difficulty.easy){
                userDailyStatus.setHardMissionCompleted(true);
            }
        }
        

        return userDailyStatus;
    }

    /**
     * ユーザデイリーステータスを取得
     * @param userId
     * @param localDate
     * @return
     */
    public UserDailyStatus getUserDailyStatus(Long userId,LocalDate localDate) {
        //なかった時のエクセプションいるかも
        UserDailyStatus userDailyStatus = userDailyStatusRepository.findByUserIdAndDate(userId,localDate);
        return userDailyStatus;
    }
    
}
