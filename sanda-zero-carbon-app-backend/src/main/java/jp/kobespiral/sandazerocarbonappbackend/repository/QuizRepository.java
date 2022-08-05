package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.Quiz;

/**
 * クイズリポジトリ 
 * 
 * @author Marin
 */
@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long>{

    List<Quiz> findAll();
    /**
     * 未回答のリスト
     * @param quizId 回答済のクイズのリスト
     * @return List<Quiz> 
     */
    List<Quiz> findByQuizIdNotIn(List<Long> quizId);
    
    /**
     * タイトルをキーワードで検索する．
     * @param keyword
     * @return List<Quiz>
     */
    List<Quiz> findByTitleContaining(String keyword);
    List<Quiz> findByTagIdContaining(String tagId);

}