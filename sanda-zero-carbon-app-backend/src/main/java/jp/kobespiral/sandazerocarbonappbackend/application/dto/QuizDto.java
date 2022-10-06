package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Quiz;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import lombok.Data;

@Data
public class QuizDto {
    Long quizId; // クイズID
    String title; // タイトル
    String quizSentence; // 問題文
    String explaination; // 説明文
    String ans1; // 選択肢１
    String ans2; // 選択肢２
    String ans3; // 選択肢３
    int point; // 獲得ポイント
    Long tagId; // タグID
    String keyword; // キーワード

    public static QuizDto build(Quiz quiz, Tag tag) {
        QuizDto dto = new QuizDto();
        dto.quizId = quiz.getQuizId();
        dto.title = quiz.getTitle();
        dto.quizSentence = quiz.getQuizSentence();
        dto.explaination = quiz.getExplaination();
        dto.ans1 = quiz.getAns1();
        dto.ans2 = quiz.getAns2();
        dto.ans3 = quiz.getAns3();
        dto.point = quiz.getPoint();
        dto.tagId = tag.getTagId();
        dto.keyword = tag.getKeyword();
        return dto;
    }
}
