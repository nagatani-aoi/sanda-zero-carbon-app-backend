package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Map;
import lombok.Data;

/**
 * マップのDto
 *
 * @author ing
 */
@Data
public class MapDto {
    String userId; // ユーザーID
    Long mapId; // マップID
    int currentLocation; // マップ上での現在地
    Long nextLocation; // 次の地点
    Long backLocation; // 1つ前の地点
    String imageSource; // ホーム画面背景の画像ソース(URL)
    String source; // マップの画像ソース(URL)

    /**
     * ユーザーIDとマップのエンティティからDtoを作成
     *
     * @param userId ユーザーID
     * @param map    マップ
     * @return マップのDto
     */
    public static MapDto build(String userId, Map map) {
        // MapDtoに各種編集をセット
        MapDto dto = new MapDto();
        dto.userId = userId;
        dto.mapId = map.getMapId();
        dto.currentLocation = map.getCurrentLocation();
        dto.nextLocation = map.getNextLocation();
        dto.backLocation = map.getBackLocation();
        dto.imageSource = map.getImageSource();
        dto.source = map.getSource();
        return dto;
    }
}
