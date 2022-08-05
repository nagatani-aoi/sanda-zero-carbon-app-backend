package jp.kobespiral.sandazerocarbonappbackend.entity;

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
    Long userId; // ユーザID
    String nickname; // ニックネーム
    int age; // 年齢
}
