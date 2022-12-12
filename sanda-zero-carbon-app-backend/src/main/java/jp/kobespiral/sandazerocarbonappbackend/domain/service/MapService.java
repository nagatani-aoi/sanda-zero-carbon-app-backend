package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MapDto;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Map;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.InitialLocationRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MapRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.utils.Rule;
import lombok.RequiredArgsConstructor;

/**
 * マップのサービス
 *
 * @author ing
 */
@Service
@RequiredArgsConstructor
public class MapService {
    /** マップのリポジトリ */
    private final MapRepository mapRepository;
    /** マップの初期一のリポジトリ */
    private final InitialLocationRepository initialLocationRepository;

    /** ユーザサービス */
    private final UserService userService;

    /* -------------------- Read -------------------- */

    /**
     * 指定したユーザーの現在地からマップを取得
     *
     * @param userId          ユーザーID
     * @param currentLocation 現在地
     * @return マップのDto
     */
    public MapDto getMap(String userId, int currentLocation) {
        int caluculatedStage = userService.getUserDto(userId).getLevel() / Rule.mapLevelRate + 1; // マップの段階を計算

        int stage; // ユーザーの現在のステージ

        if (caluculatedStage > Rule.maxStage) { // 計算したステージが最大値を超えていたら
            stage = Rule.maxStage; // ステージの最大値に設定
        } else {
            stage = caluculatedStage; // 計算したステージに設定
        }

        Map map = mapRepository.findByCurrentLocationAndStage(currentLocation, stage); // 特定ユーザーのレベルと現在地からマップを取得

        return MapDto.build(userId, map);
    }

    /**
     * 指定したユーザーのマップの初期値情報を取得
     *
     * @param userId
     * @return MapDto
     */
    public MapDto getMapOnInitialLocation(String userId) {
        int caluculatedStage = userService.getUserDto(userId).getLevel() / Rule.mapLevelRate + 1; // マップの段階を計算

        int stage; // ユーザーの現在のステージ

        if (caluculatedStage > Rule.maxStage) { // 計算したステージが最大値を超えていたら
            stage = Rule.maxStage; // ステージの最大値に設定
        } else {
            stage = caluculatedStage; // 計算したステージに設定
        }

        Map map = mapRepository.findByCurrentLocationAndStage(
                initialLocationRepository.findFirstByStage(stage).getInitialLocation(), stage); // 特定ユーザーのレベルから初期位置のマップを取得

        return MapDto.build(userId, map);
    }
}
