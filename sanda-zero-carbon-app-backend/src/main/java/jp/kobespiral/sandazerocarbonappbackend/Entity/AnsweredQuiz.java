package jp.kobespiral.sandazerocarbonappbackend.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
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
    Long answeredQuizId; // 回答済みクイズID
    Long userId; // ユーザID
    Long quizId; // クイズID
    LocalDateTime answeredAt; // 回答完了日時
    String ans; // 正解
    Boolean isCorrected; // 正誤フラグ
}
