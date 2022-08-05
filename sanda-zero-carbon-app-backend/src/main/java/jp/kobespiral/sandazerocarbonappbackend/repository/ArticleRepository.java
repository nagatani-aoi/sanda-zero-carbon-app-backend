package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.Article;
/**
 * 記事のリポジトリ
 * @author sato
 */
@Repository
public interface ArticleRepository extends CrudRepository<Article,Long>{
    /**
     * タグIDで記事を検索する
     * @param tagId タグID
     * @return 指定されたタグIDでフィルター済みの済みの記事のリスト
     */
    List<Article> findByTagId(Long tagId);
    /**
     * 重要度の高い記事を検索する
     * @return 重要度の高い記事のリスト
     */
    List<Article> findByIsImportantTrue();
    /**
     * キーワードで記事のタイトルを検索する
     * @param title タイトル（キーワード）
     * @return 指定されたキーワードをタイトルに含む記事のリスト
     */
    List<Article> findByTitleContaining(String title);
}