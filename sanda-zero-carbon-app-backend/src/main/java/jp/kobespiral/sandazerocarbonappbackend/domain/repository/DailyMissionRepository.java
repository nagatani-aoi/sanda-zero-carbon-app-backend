package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.DailyMission;

/**
 * デイリーミッションリポジトリ
 * 
 * @author Marin
 */
@Repository
public interface DailyMissionRepository extends CrudRepository<DailyMission, Long> {
    /**
     * 指定した日以降のデイリーミッションを取得する．
     * 
     * @param date 日にち
     * @return 指定された日以降のデイリーミッションのリスト
     */
    List<DailyMission> findByDateGreaterThanEqual(LocalDate date);
}
