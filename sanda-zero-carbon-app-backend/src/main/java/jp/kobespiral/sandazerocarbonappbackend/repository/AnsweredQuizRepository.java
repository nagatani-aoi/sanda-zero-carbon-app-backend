package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.AnsweredQuiz;

/**
 * 回答済みクイズ リポジトリ
 * 
 * @author Kamae
 */
@Repository
public interface AnsweredQuizRepository extends CrudRepository<AnsweredQuiz, Long> {
    /**
     * ユーザIDを指定して，回答済みクイズのリストを取得する
     * @param userId ユーザID
     * @return List<AnsweredQuiz> 回答済みクイズのリスト
     */
    List<AnsweredQuiz> findByUserId(Long userId);
    /**
     * 正誤フラグとユーザIDを指定して，該当する回答済みリストを取得する
     * @param isCorrected 正誤フラグ
     * @param userId ユーザID
     * @return List<AnsweredQuiz> 回答済みクイズのリスト
     */
    List<AnsweredQuiz> findByIsCorrectedAndUserId(Boolean isCorrected, Long userId);
}