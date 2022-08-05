package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.Date;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDailyStatusRepository extends CrudRepository<UserDailyStatus,Long>{
    List<UserDailyStatus> findByUserIdAndDateBetween(Long userId, Date since, Date until);
    List<UserDailyStatus> findByUserIdAndDate(Long userId, Date date);
}
