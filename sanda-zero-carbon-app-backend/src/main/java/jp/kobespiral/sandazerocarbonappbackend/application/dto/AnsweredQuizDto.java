package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.AnsweredQuiz;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Quiz;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import lombok.Data;

@Data
public class AnsweredQuizDto {
    Long answeredQuizId; // 回答済みクイズID
    Long quizId; // クイズID
    String userId; // ユーザID
    LocalDateTime answeredAt; // 回答完了日時
    String userAns; // 回答
    Boolean isCorrect; // 正誤フラグ

    // 以下、クイズIDに紐づけられたクイズの中身に関する情報
    String title; // タイトル
    String quizSentence; // 問題文
    String explaination; // 説明文

    String correctAns; // 正解
    int point; // 獲得ポイント
    Long tagId; // タグID
    String keyword; // キーワード

    public static AnsweredQuizDto build(AnsweredQuiz answeredQuiz, Quiz quiz, Tag tag) {
        AnsweredQuizDto dto = new AnsweredQuizDto();

        dto.answeredQuizId = answeredQuiz.getAnsweredQuizId();
        dto.userId = answeredQuiz.getUserId();
        dto.quizId = answeredQuiz.getQuizId();
        dto.answeredAt = answeredQuiz.getAnsweredAt();
        dto.userAns = answeredQuiz.getUserAns();
        dto.isCorrect = answeredQuiz.getIsCorrect();

        dto.title = quiz.getTitle();
        dto.quizSentence = quiz.getQuizSentence();
        dto.explaination = quiz.getExplaination();

        dto.correctAns = quiz.getCorrectAns();
        dto.point = quiz.getPoint();
        dto.tagId = tag.getTagId();
        dto.keyword = tag.getKeyword();

        return dto;
    }
}
