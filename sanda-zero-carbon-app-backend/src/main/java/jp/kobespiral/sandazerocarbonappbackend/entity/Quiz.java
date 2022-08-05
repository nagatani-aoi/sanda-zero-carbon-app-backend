package jp.kobespiral.sandazerocarbonappbackend.entity;

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
 * @author Marin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long quizId; 
    String title; 
    String quizSentence; // 問題文
    String explain; // 説明文
    String ans1; 
    String ans2; 
    String ans3; 
    int point; 
    Long tagId; 
}
