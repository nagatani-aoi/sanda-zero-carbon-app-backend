package jp.kobespiral.sandazerocarbonappbackend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * アドミン エンティティ
 * 
 * @author Kamae
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Administrator {
    @Id
    Long adminId; // アドミンID
    String password; // パスワード
}