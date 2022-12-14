package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * マップ エンティティ
 *
 * @author Kamae
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long mapId; // マップID
    int currentLocation; // マップ上での現在地
    int stage; // 段階
    Long nextLocation; // 次の地点
    Long backLocation; // 1つ前の地点
    @Lob
    @Column(name = "mapImage", columnDefinition = "LONGBLOB", nullable = false)
    String mapImage; // マップの画像データ
    @Lob
    @Column(name = "placeImage", columnDefinition = "LONGBLOB", nullable = false)
    String placeImage; // 名勝の画像データ
    String placeName; // 名勝の名称
    @Column(length = 100000)
    String placeLink; // 名勝のリンク
}
