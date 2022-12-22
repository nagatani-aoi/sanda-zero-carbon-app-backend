package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MapDto;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Map;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.InitialLocationRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MapRepository;
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
        int stage = userService.calculateStage(userId);// ステージを計算

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
        int stage = userService.calculateStage(userId);// ステージを計算

        Map map = mapRepository.findByCurrentLocationAndStage(
                initialLocationRepository.findFirstByStage(stage).getInitialLocation(), stage); // 特定ユーザーのレベルから初期位置のマップを取得

        return MapDto.build(userId, map);
    }
}