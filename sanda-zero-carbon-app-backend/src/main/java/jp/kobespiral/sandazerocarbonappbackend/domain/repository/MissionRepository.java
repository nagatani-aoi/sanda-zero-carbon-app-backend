package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Difficulty;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;

/**
 * ミッションリポジトリ
 * 
 */
@Repository
public interface MissionRepository extends CrudRepository<Mission, Long> {

    /**
     * タイトルをキーワードで検索する．
     * 
     * @param keyword キーワード
     * @return 指定されたキーワードをタイトルに含むミッションのリスト
     */
    List<Mission> findByTitleContaining(String keyword);

    /**
     * タグIDで記事を検索する
     * 
     * @param tagId タグID
     * @return 指定されたタグIDでフィルター済みの記事のリスト
     */
    List<Mission> findByTagId(Long tagId);

    /**
     * ミッションをすべて取得する
     * 
     * @return すべてのミッションのリスト
     */
    List<Mission> findAll();

    /**
     * 指定した難易度のミッションのリストを取得する
     * 
     * @param difficulty ミッションの難易度
     * @return 指定した難易度のミッションのリスト
     */
    List<Mission> findByDifficulty(Difficulty difficulty);

}
