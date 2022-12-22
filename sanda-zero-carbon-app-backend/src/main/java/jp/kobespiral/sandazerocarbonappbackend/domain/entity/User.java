package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import javax.persistence.Entity;
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
    String userId; // ユーザID
    String password;//パスワード
    int age; // 年齢
    int totalPoint;//累積ポイント
    Boolean levelFlag;//レベルアップフラグ
    Boolean mapFlag;//マップ解放フラグ
}
