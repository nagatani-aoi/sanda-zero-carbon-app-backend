package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TotalCondition extends CrudRepository<TotalCondition,Long>{
    List<TotalCondition> findAll();
    List<TotalCondition> findByTotalBetween(Date since, Date until);
}
