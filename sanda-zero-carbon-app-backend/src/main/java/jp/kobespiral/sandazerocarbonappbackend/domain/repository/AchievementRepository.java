package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Achievement;

/**
 * 達成 リポジトリ
 * 
 * @author Kamae
 */
@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Long> {
    /**
     * 指定した日時範囲内の達成ミッションのリストを返す
     *
     * @param since いつから
     * @param until いつまで
     * @return 指定した日時範囲内の達成ミッションのリスト
     */
    List<Achievement> findByAchievedAtBetween(LocalDateTime since, LocalDateTime until);

    /**
     * ユーザIDを指定して，達成ミッションのリストを取得する
     * 
     * @param userId ユーザID
     * @return List<Achievement> 達成ミッションのリスト
     */
    List<Achievement> findByUserId(String userId);

    /**
     * ユーザIDの指定と達成日時の範囲指定を行い，
     * 該当する達成ミッションのリストを返す
     * 
     * @param userId ユーザID
     * @param since  集計開始日時
     * @param until  集計終了日時
     * @return List<Achievement> 達成ミッションのリスト
     */
    List<Achievement> findByUserIdAndAchievedAtBetween(String userId, LocalDateTime since, LocalDateTime until);
}