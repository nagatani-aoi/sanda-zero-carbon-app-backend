package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.InitialLocation;

/**
 * 初期位置のリポジトリ
 * 
 * @author sato
 */
@Repository
public interface InitialLocationRepository extends CrudRepository<InitialLocation, Long> {
    /**
     * そのレベルでの到達可能なマスの最大のマス目を返す。これにより、最初にいるマス目がサービスで使える
     * 
     * @param level
     * @return そのレベルにおいて、到達可能なマスの最大のマス目を返す。
     */
    /* (注意)書き換えてる */
    InitialLocation findFirstByLevelLessThanEquals(int level);
}
