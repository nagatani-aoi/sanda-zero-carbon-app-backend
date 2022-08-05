package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.Tag;

/**
 * タグ リポジトリ
 * 
 * @author Kamae
 */
@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    /**
     * タグをすべて取得する
     * @param
     * @return タグのリスト
     */
    List<Tag> findAll();
}