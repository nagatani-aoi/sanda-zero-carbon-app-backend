package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * ミッション エンティティ
 * 
 * @author Marin
 */
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long missionId; // ミッションID
    int point; // ポイント
    String title; // 題目
    String description; // 説明
    double CO2Reduction; // CO2削減量
    double costReduction; // 節約金額
    Difficulty difficulty; // 難易度
    MissionType missionType; // ミッションタイプ
    Long tagId; // タグID
}
