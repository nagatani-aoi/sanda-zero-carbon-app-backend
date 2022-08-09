package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Difficulty;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.MissionType;
import lombok.Data;

@Data
public class MissionForm {
    int point; // ポイント
    String title; // 題目
    String description; // 説明
    double CO2Reduction; // CO2削減量
    double costReduction; // 節約金額
    Difficulty difficulty; // 難易度
    MissionType missionType; // ミッションタイプ
    Long tagId; // タグID

    public Mission toEntity() {
        Mission mission = new Mission();
        mission.setPoint(point);
        mission.setTitle(title);
        mission.setDescription(description);
        mission.setCo2Reduction(CO2Reduction);
        mission.setCostReduction(costReduction);
        mission.setDifficulty(difficulty);
        mission.setMissionType(missionType);
        mission.setTagId(tagId);
        return mission;
    }
}
