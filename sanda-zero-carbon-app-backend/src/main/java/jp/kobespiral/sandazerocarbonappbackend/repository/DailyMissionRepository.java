package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.DailyMission;
import jp.kobespiral.sandazerocarbonappbackend.entity.Mission;

/**
 * デイリーミッションリポジトリ 
 * 
 * @author Marin
 */
@Repository
public interface DailyMissionRepository extends CrudRepository<Mission, Long>{
    /**
     * 指定した日以降のデイリーミッションを取得する．
     * @param date
     * @return List<DailyMission>
     */
    List<DailyMission> findByDateGreaterThanEqual(Date date);  
}
