package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.Achievement;

/**
 * 達成 リポジトリ
 * 
 * @author Kamae
 */
@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Long> {
    /**
     * 指定した日時以降の達成ミッションのリストを返す
     * @param date 達成日時
     * @return List<DailyMission> 達成ミッションのリスト
     */
    List<Achievement> findByDateGreaterThanEqual(LocalDateTime date);

    /**
     * ユーザIDを指定して，達成ミッションのリストを取得する
     * @param userId ユーザID
     * @return List<Achievement> 達成ミッションのリスト
     */
    List<Achievement> findByUserId(Long userId);

    /**
     * ユーザIDの指定と達成日時の範囲指定を行い，
     * 　該当する達成ミッションのリストを返す
     * @param userId ユーザID
     * @param since 集計開始日時
     * @param until 集計終了日時
     * @return List<Achievement> 達成ミッションのリスト
     */
    List<Achievement> findByUserIdAndAchievedAtBetween(Long userId, LocalDateTime since, LocalDateTime until);
}