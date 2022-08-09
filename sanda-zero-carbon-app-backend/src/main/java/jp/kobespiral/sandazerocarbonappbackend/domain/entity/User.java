package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザ エンティティ
 * 
 * @author Kamae
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId; // ユーザID
    String nickname; // ニックネーム
    String password;//パスワード
    int age; // 年齢
    int totalPoint;//累積ポイント
}
