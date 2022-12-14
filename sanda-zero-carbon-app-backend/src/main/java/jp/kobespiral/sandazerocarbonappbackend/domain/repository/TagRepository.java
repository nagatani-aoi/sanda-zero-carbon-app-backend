package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;

/**
 * タグ リポジトリ
 * 
 * @author sato
 */
@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    /**
     * タグをすべて取得する
     * 
     * @param
     * @return タグのリスト
     */
    List<Tag> findAll();
    /**
     * タグがすでに存在するかどうか
     * @param keyword キーワード
     * @return 存在する(true)かしない(false)か
     */
    Boolean existsByKeyword(String keyword);
}