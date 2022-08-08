package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ミッション エンティティ
 *
 * @author Marin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
