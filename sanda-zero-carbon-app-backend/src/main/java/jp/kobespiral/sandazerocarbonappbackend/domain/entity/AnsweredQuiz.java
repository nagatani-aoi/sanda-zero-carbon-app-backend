package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回答済み クイズエンティティ
 * 
 * @author Kamae
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AnsweredQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long answeredQuizId; // 回答済みクイズID
    Long quizId; // クイズID
    String userId; // ユーザID
    // @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime answeredAt; // 回答完了日時
    int userAnsNum; // 回答番号
    Boolean isCorrect; // 正誤フラグ
}
