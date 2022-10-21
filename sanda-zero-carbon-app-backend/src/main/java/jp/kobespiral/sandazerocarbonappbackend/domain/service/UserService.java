package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserDailyDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.UserValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Achievement;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.AnsweredQuiz;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Difficulty;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Quiz;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.UserDailyStatus;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.AchievementRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.AnsweredQuizRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.QuizRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.UserDailyStatusRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.UserRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

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
    @Autowired
    AnsweredQuizRepository answeredQuizRepository;
    @Autowired
    QuizRepository quizRepository;

    /**
     * ユーザ作成
     * 
     * @param form ユーザフォーム
     * @return リポジトリにセーブ
     */
    public User createUser(UserForm form) {
        String userId = form.getUserId();
        if (userRepository.existsById(userId)) {
            throw new UserValidationException(USER_ALREADY_EXISTS, "create user",
                    String.format("userId : %s has already exist.", userId));
        }
        User user = form.toEntity();
        if (user.getPassword() == "" || user.getPassword() == null) {
            String pass = getRandomString(10);
            Charset charset = StandardCharsets.UTF_8;
            // エンコード実行
            String encodedPass = Base64.getEncoder().encodeToString(pass.getBytes(charset));
            user.setPassword(encodedPass);
        }
        return userRepository.save(user);
    }

    /**
     * ユーザを取得
     * 
     * @param userId ユーザID
     * @return ユーザ
     */
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserValidationException(USER_DOES_NOT_EXIST,
                "get user", String.format("userId : %s does't exist", userId)));
        return user;
    }

    /**
     * ユーザの存在の有無を調べる
     * 
     * @param userId ユーザID
     * @return 存在するかしないか
     */
    public Boolean isUserExist(String userId) {
        if (userRepository.existsById(userId)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ログイン用サービス
     * 
     * @param userId   ユーザID
     * @param password パスワード
     * @return
     */
    public User loginUser(String userId, String password) {
        // 存在していたら
        if (userRepository.existsByUserIdAndPassword(userId, password)) {
            User user = userRepository.findByUserIdAndPassword(userId, password);
            return user;
        } else {
            throw new UserValidationException(USER_DOES_NOT_EXIST, "login user",
                    String.format("userId : %s,password : %s doesn't exist", userId, password));
        }
    }

    /**
     * ユーザdtoを取得。メイン画面構成時に使用
     * 
     * @param userId
     * @return ユーザdto
     */
    public UserDto getUserDto(String userId) {
        // 微妙
        User user = userRepository.findById(userId).orElseThrow(() -> new UserValidationException(USER_DOES_NOT_EXIST,
                "get userDto", String.format("userId : %s doesn't exits", userId)));
        return UserDto.build(user);
    }

    /**
     * ユーザデイリーステータスDtoの取得
     * 
     * @param userId ユーザID
     * @return ユーザデイリーステータスDto
     */
    public UserDailyDto getUserDailyDto(String userId) {

        UserDailyStatus userDailyStatus = getUserDailyStatus(userId);
        return UserDailyDto.build(userDailyStatus);
    }

    /**
     * ユーザデイリーステータスの更新(ミミッション用)
     * 
     * @param userId        ユーザID
     * @param achievementId 達成ID
     * @return ユーザデイリーステータス
     */

    public UserDailyStatus renewUserDailyStatusForMission(String userId, Long achievementId) {
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new UserValidationException(USER_DOES_NOT_EXIST, "create the user",
                        String.format("crate %d", userId)));
        // 日付タイプの変更
        LocalDateTime localDateTime = achievement.getAchievedAt();
        LocalDate localDate = localDateTime.toLocalDate();
        // その日のデイリーステータスが存在しなければ、新たに作成
        if (!userDailyStatusRepository.existsByUserIdAndDate(userId, localDate)) {
            UserDailyStatus tempUserDailyStatus = new UserDailyStatus(null, userId, localDate, 0, 0, 0, 0, false, false,
                    false);
            userDailyStatusRepository.save(tempUserDailyStatus);
        }
        Mission mission = missionRepository.findById(achievement.getMissionId())
                .orElseThrow(() -> new UserValidationException(USER_DOES_NOT_EXIST, "create the user",
                        String.format("crate %d", userId)));

        UserDailyStatus userDailyStatus = userDailyStatusRepository.findByUserIdAndDate(userId, localDate);

        // ユーザのトータルポイントの更新
        User user = getUser(userId);
        user.setTotalPoint(user.getTotalPoint() + achievement.getGetPoint());
        userRepository.save(user);
        // デイリーステータスの更新
        userDailyStatus.setTotalMissionPoint(userDailyStatus.getTotalMissionPoint() + achievement.getGetPoint());
        userDailyStatus.setTotalCo2Reduction(userDailyStatus.getTotalCo2Reduction() + mission.getCo2Reduction());
        userDailyStatus.setTotalCostReduction(userDailyStatus.getTotalCostReduction() + mission.getCostReduction());
        // もしデイリーミッションならばフラグ更新
        if (achievement.getIsDailyMission()) {
            if (mission.getDifficulty() == Difficulty.easy) {
                userDailyStatus.setEasyMissionCompleted(true);
            } else if (mission.getDifficulty() == Difficulty.normal) {
                userDailyStatus.setNormalMissionCompleted(true);
                ;
            } else if (mission.getDifficulty() == Difficulty.hard) {
                userDailyStatus.setHardMissionCompleted(true);
            }
        }
        userDailyStatusRepository.save(userDailyStatus);

        return userDailyStatus;
    }

    /**
     * ユーザデイリーステータスの更新(クイズ用)
     * 
     * @param userId        ユーザID
     * @param achievementId 達成ID
     * @return ユーザデイリーステータス
     */

    public UserDailyStatus renewUserDailyStatusForQuiz(String userId, Long answeredQuizId) {
        AnsweredQuiz answeredQuiz = answeredQuizRepository.findById(answeredQuizId)
                .orElseThrow(() -> new UserValidationException(USER_DOES_NOT_EXIST, "create the user",
                        String.format("crate %d", userId)));
        // 日付タイプの変更
        LocalDateTime localDateTime = answeredQuiz.getAnsweredAt();
        LocalDate localDate = localDateTime.toLocalDate();
        // その日のデイリーステータスが存在しなければ、新たに作成
        if (!userDailyStatusRepository.existsByUserIdAndDate(userId, localDate)) {
            UserDailyStatus tempUserDailyStatus = new UserDailyStatus(null, userId, localDate, 0, 0, 0, 0, false, false,
                    false);
            userDailyStatusRepository.save(tempUserDailyStatus);
        }
        Quiz quiz = quizRepository.findById(answeredQuiz.getQuizId())
                .orElseThrow(() -> new UserValidationException(USER_DOES_NOT_EXIST, "create the user",
                        String.format("crate %d", userId)));
        UserDailyStatus userDailyStatus = userDailyStatusRepository.findByUserIdAndDate(userId, localDate);

        // ユーザのトータルポイントの更新
        User user = getUser(userId);
        user.setTotalPoint(user.getTotalPoint() + quiz.getPoint());
        userRepository.save(user);
        // デイリーステータスの更新
        userDailyStatus.setTotalQuizPoint(userDailyStatus.getTotalQuizPoint() + quiz.getPoint());
        userDailyStatusRepository.save(userDailyStatus);

        return userDailyStatus;
    }

    /**
     * ユーザデイリーステータスを取得
     * 
     * @param userId
     * @return
     */
    // @Transactional
    public synchronized UserDailyStatus getUserDailyStatus(String userId) {
        LocalDate localDate = LocalDate.now();
        UserDailyStatus userDailyStatus;

        // その日のユーザデイリーステータスがなかったら作成
        if (userDailyStatusRepository.existsByUserIdAndDate(userId, localDate)) {
            userDailyStatus = userDailyStatusRepository.findByUserIdAndDate(userId, localDate);
        } else {
            UserDailyStatus tempUserDailyStatus = new UserDailyStatus(null, userId, localDate, 0, 0, 0, 0, false, false,
                    false);
            userDailyStatusRepository.save(tempUserDailyStatus);
            userDailyStatus = tempUserDailyStatus;
        }
        return userDailyStatus;
    }

    /**
     * パスワードを変更する
     * 
     * @param userId   ユーザID
     * @param password 新しいパスワード
     * @return パスワードを変更したユーザエンティティ
     */
    public User changePassword(String userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserValidationException(USER_DOES_NOT_EXIST,
                "change user password", String.format("userId : %s doesn't exist", userId)));
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    /**
     * ランダムパスワード生成
     * 
     * @param i パスワードの長さ
     * @return 長さiのランダム文字列
     */
    static String getRandomString(int i) {
        String theAlphaNumericS;
        StringBuilder builder;
        theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
        // create the StringBuffer
        builder = new StringBuilder(i);
        for (int m = 0; m < i; m++) {

            // generate numeric
            int myindex = (int) (theAlphaNumericS.length()
                    * Math.random());

            // add the characters
            builder.append(theAlphaNumericS
                    .charAt(myindex));
        }
        return builder.toString();
    }
}
