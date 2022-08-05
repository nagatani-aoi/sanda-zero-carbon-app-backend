package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.Mission;

/**
 * ミッションリポジトリ 
 */
@Repository
public interface MissionRepository extends CrudRepository<Mission, Long>{

    /**
     * タイトルをキーワードで検索する．
     * @param keyword
     * @return List<Mission>
     */
    List<Mission> findByDateTitleContaining(String keyword); 
    
    /**
     * タグIDで検索する．
     * @param tagId
     * @return List<Mission>
     */
    List<Mission> findByTagId(Long tagId); 

}
