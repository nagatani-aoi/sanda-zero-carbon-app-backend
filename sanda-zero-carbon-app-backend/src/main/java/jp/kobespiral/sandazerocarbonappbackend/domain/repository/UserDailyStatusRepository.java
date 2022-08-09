package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.UserDailyStatus;

/**
 * ユーザデイリーステータスのリポジトリ
 * 
 * @author sato
 */
@Repository
public interface UserDailyStatusRepository extends CrudRepository<UserDailyStatus, Long> {
    /**
     * ユーザIDと期間を指定して、ユーザデイリーステータスのリストを取得する
     * 
     * @param userId ユーザID
     * @param since  指定期間の始まり
     * @param until  指定期間の終わり
     * @return ユーザIDと指定期間でフィルター済みのユーザデイリーステータスのリスト
     */
    List<UserDailyStatus> findByUserIdAndDateBetween(String userId, LocalDate since, LocalDate until);

    /**
     * ユーザIDと日時を指定して、ユーザデイリーステータスのリストを取得する
     * 
     * @param userId ユーザID
     * @param date   指定する日時
     * @return ユーザIDと日時でフィルター済みのユーザデイリーステータスのリスト
     */
    UserDailyStatus findByUserIdAndDate(String userId, LocalDate date);
    Boolean existsByUserIdAndDate(String userId, LocalDate date);
}
