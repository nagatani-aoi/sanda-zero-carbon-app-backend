package jp.kobespiral.sandazerocarbonappbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.Map;

/**
 * マップ リポジトリ
 * 
 * @author Kamae
 */
@Repository
public interface MapRepository extends CrudRepository<Map, Long> {
    /**
     * マップ上での現在地とレベルを指定して，マップを返す
     * @param currentLocation マップ上での現在地
     * @param level レベル
     * @return Optional<Map> マップ
     */
    Map findByCurrentLocationAndLevel(Long currentLocation, int level);
}