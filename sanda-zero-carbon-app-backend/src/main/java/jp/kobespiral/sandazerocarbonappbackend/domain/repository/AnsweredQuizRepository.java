package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.AnsweredQuiz;

/**
 * 回答済みクイズ リポジトリ
 * 
 * @author Kamae
 */
@Repository
public interface AnsweredQuizRepository extends CrudRepository<AnsweredQuiz, Long> {
    /**
     * ユーザIDとクイズIDで回答済みクイズを取得
     *
     * @param userId
     * @param quizId
     * @return List<AnsweredQuiz>
     */
    List<AnsweredQuiz> findByUserIdAndQuizId(String userId, Long quizId);

    /**
     * ユーザIDとクイズIDで存在判定
     *
     * @param userId
     * @param quizId
     * @return
     */
    Boolean existsByUserIdAndQuizId(String userId, Long quizId);

    /**
     * ユーザID，クイズIDでクイズを検索し，正解したことがあるかを判定
     */
    Boolean existsByUserIdAndQuizIdAndIsCorrectTrue(String userId, Long quizId);
}