package jp.kobespiral.sandazerocarbonappbackend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

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
    Long mapId; // マップID
    Long CurrentLocation; // マップ上での現在地
    int level; // レベル
    Long nextLocation; // 次の地点
    Long backLocation; // 1つ前の地点
    String imageSource; // ホーム画面背景の画像ソース(URL)
    String source; // マップの画像ソース(URL)
}
