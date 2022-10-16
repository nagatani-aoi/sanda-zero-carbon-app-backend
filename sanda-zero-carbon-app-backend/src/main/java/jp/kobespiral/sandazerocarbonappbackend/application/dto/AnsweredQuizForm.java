package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.AnsweredQuiz;
import lombok.Data;

@Data
public class AnsweredQuizForm {
    @NotNull
    Long quizId; // クイズID
    @NotBlank
    String userId; // ユーザID
    @NotBlank
    String userAns; // 回答

    public AnsweredQuiz toEntity() {
        AnsweredQuiz answeredQuiz = new AnsweredQuiz();
        answeredQuiz.setUserId(userId);
        answeredQuiz.setQuizId(quizId);
        answeredQuiz.setAnsweredAt(LocalDateTime.now());
        answeredQuiz.setUserAns(userAns);
        answeredQuiz.setIsCorrect(false);
        return answeredQuiz;
    }
}
