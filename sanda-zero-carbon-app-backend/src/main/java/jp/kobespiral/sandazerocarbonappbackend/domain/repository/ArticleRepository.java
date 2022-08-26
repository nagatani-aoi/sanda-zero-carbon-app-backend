package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Article;

/**
 * 記事のリポジトリ
 * 
 * @author sato
 */
@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    /**
     * 全ての記事を取得する
     * @return 全ての記事
     */
    List<Article> findAllByOrderByPostedAtDesc();
    /**
     * タグIDで記事を検索する
     * 
     * @param tagId タグID
     * @return 指定されたタグIDでフィルター済みの済みの記事のリスト
     */
    List<Article> findByTagIdOrderByPostedAtDesc(Long tagId);

    /**
     * 重要度の高い記事を検索する
     * 
     * @return 重要度の高い記事のリスト
     */
    List<Article> findByIsImportantTrue();

    /**
     * キーワードで記事のタイトルを検索する
     * 
     * @param title タイトル（キーワード）
     * @return 指定されたキーワードをタイトルに含む記事のリスト
     */
    List<Article> findByTitleContaining(String title);
}