package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ステージ毎のマップの初期位置のエンティティ
 *
 * @author sato checked ing
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InitialLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long initialLocationId; // 初期位置のID
    int stage; // ステージ
    int initialLocation; // 初期位置
}
