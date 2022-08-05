package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.Mission;

/**
 * ミッションリポジトリ 
 * 
 */
@Repository
public interface MissionRepository extends CrudRepository<Mission, Long>{

    /**
     * タイトルをキーワードで検索する．
     * @param keyword　キーワード
     * @return 指定されたキーワードをタイトルに含むミッションのリスト
     */
    List<Mission> findByDateTitleContaining(String keyword); 
    
    /**
     * タグIDで記事を検索する
     * @param tagId タグID
     * @return 指定されたタグIDでフィルター済みの記事のリスト
     */
    List<Mission> findByTagId(Long tagId); 

}
