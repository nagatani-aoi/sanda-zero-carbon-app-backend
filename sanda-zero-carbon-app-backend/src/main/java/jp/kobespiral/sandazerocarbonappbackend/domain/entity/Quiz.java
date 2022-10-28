package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * クイズ エンティティ
 *
 * @author Marin checked ing
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long quizId; // クイズID
    String title; // タイトル
    String quizSentence; // 問題文
    String explaination; // 解説文
    String ans1; // 選択肢１
    String ans2; // 選択肢２
    String ans3; // 選択肢３
    String ans4; // 選択肢４
    String correctAns; // 解答
    int point; // 獲得ポイント
    Long tagId; // タグID
}
