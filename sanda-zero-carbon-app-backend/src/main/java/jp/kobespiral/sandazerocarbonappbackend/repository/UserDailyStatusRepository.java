package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.Date;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * @author sato
 */
@Repository
public interface UserDailyStatusRepository extends CrudRepository<UserDailyStatus,Long>{
    /**
     * ユーザIDと期間を指定して、ユーザデイリーステータスのリストを取得する
     * @param userId ユーザID
     * @param since 指定期間の始まり
     * @param until 指定期間の終わり
     * @return ユーザIDと指定期間でフィルター済みのユーザデイリーステータスのリスト
     */
    List<UserDailyStatus> findByUserIdAndDateBetween(Long userId, Date since, Date until);
    /**
     * ユーザIDと日時を指定して、ユーザデイリーステータスのリストを取得する
     * @param userId ユーザID
     * @param date 指定する日時
     * @return ユーザIDと日時でフィルター済みのユーザデイリーステータスのリスト
     */
    List<UserDailyStatus> findByUserIdAndDate(Long userId, Date date);
}
