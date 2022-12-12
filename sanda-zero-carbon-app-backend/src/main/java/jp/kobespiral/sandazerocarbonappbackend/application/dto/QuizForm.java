package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Quiz;
import lombok.Data;

@Data
public class QuizForm {
    @NotBlank
    @Length(min = 1, max = 100)
    String title; // タイトル
    @NotBlank
    @Length(min = 1, max = 1000)
    String quizSentence; // 問題文
    @NotBlank
    @Length(min = 1, max = 1000)
    String explaination; // 説明文
    // explain
    @NotBlank
    @Length(min = 1, max = 100)
    String ans1; // 選択肢１
    @NotBlank
    @Length(min = 1, max = 100)
    String ans2; // 選択肢２
    @Length(min = 0, max = 100)
    String ans3; // 選択肢３
    @Length(min = 0, max = 100)
    String ans4; // 選択肢４
    // 1 ~ 4の数字範囲制約必要
    // @Length(min = 1, max = 100)
    int correctAnsNum; // 正解
    @NotNull
    @Positive
    int point; // 獲得ポイント
    @NotNull
    Long tagId; // タグID

    public Quiz toEntity() {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuizSentence(quizSentence);
        quiz.setExplaination(explaination);
        quiz.setAns1(ans1);
        quiz.setAns2(ans2);
        quiz.setAns3(ans3);
        quiz.setAns4(ans4);
        quiz.setCorrectAnsNum(correctAnsNum);
        quiz.setPoint(point);
        quiz.setTagId(tagId);
        return quiz;
    }
}
