package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Quiz;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import lombok.Data;

@Data
public class QuizDto {
    Long quizId; // クイズID
    String title; // タイトル
    String quizSentence; // 問題文
    String explaination; // 説明文
    List<String> answerList; // 選択肢リスト
    int correctAnsNum; // 正解
    int point; // 獲得ポイント
    Long tagId; // タグID
    String keyword; // キーワード

    public static QuizDto build(Quiz quiz, Tag tag) {
        QuizDto dto = new QuizDto();
        dto.quizId = quiz.getQuizId();
        dto.title = quiz.getTitle();
        dto.quizSentence = quiz.getQuizSentence();
        dto.explaination = quiz.getExplaination();
        if (quiz.getAns4() == null) {
            if (quiz.getAns3() == null) {
                List<String> list = new ArrayList<String>(Arrays.asList(quiz.getAns1(), quiz.getAns2()));
                dto.answerList = list;
            } else {
                List<String> list = new ArrayList<String>(
                        Arrays.asList(quiz.getAns1(), quiz.getAns2(), quiz.getAns3()));
                dto.answerList = list;
            }
        } else {
            if (quiz.getAns3() != null) {
                List<String> list = new ArrayList<String>(
                        Arrays.asList(quiz.getAns1(), quiz.getAns2(), quiz.getAns3(), quiz.getAns4()));
                dto.answerList = list;
            }
        }
        // dto.ans1 = quiz.getAns1();
        // dto.ans2 = quiz.getAns2();
        // dto.ans3 = quiz.getAns3();
        // dto.ans4 = quiz.getAns4();
        dto.correctAnsNum = quiz.getCorrectAnsNum();
        dto.point = quiz.getPoint();
        dto.tagId = tag.getTagId();
        dto.keyword = tag.getKeyword();
        return dto;
    }
}
