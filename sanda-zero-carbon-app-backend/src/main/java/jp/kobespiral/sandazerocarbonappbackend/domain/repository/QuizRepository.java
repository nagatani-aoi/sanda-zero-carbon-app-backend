package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Quiz;

/**
 * クイズリポジトリ
 * 
 * @author Marin
 */
@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

    /**
     * 全てのクイズのリストを取得する．
     */
    List<Quiz> findAll();

    /**
     * 未回答のリストを取得する．
     * 
     * @param quizId 回答済のクイズのリスト
     * @return 未回答のクイズのリスト
     */
    List<Quiz> findByQuizIdNotIn(List<Long> quizId);

    /**
     * キーワードでクイズを検索する．
     * 
     * @param keyword キーワード
     * @return 指定されたキーワードをタイトルに含む記事のリスト
     */
    List<Quiz> findByTitleContaining(String keyword);

    /**
     * タグIDでクイズを検索する
     * 
     * @param tagId タグID
     * @return 指定されたタグIDでフィルター済みのクイズのリスト
     */
    List<Quiz> findByTagIdContaining(String tagId);

}