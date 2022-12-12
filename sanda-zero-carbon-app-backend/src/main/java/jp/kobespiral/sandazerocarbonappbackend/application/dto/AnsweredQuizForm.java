package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.AnsweredQuiz;
import lombok.Data;

/**
 * クイズの回答フォーム
 *
 * @author ing
 */
@Data
public class AnsweredQuizForm {
    @NotNull
    Long quizId; // クイズID
    @NotBlank
    String userId; // ユーザID
    // 1 ~ 4 のバリデーション必要
    // @NotBlank
    int userAnsNum; // 回答番号

    public AnsweredQuiz toEntity() {
        AnsweredQuiz answeredQuiz = new AnsweredQuiz();
        answeredQuiz.setUserId(userId);
        answeredQuiz.setQuizId(quizId);
        answeredQuiz.setAnsweredAt(LocalDateTime.now());
        answeredQuiz.setUserAnsNum(userAnsNum);
        answeredQuiz.setIsCorrect(false);
        return answeredQuiz;
    }
}
