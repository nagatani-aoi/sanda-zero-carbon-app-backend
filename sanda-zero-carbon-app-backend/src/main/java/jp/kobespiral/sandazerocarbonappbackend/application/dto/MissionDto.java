package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Difficulty;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.MissionType;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import lombok.Data;

@Data
public class MissionDto {
    Long missionId; // ミッションID
    int point; // ポイント
    String title; // 題目
    String description; // 説明
    double CO2Reduction; // CO2削減量
    double costReduction; // 節約金額
    Difficulty difficulty; // 難易度
    MissionType missionType; // ミッションタイプ
    Long tagId; // タグID
    String keyword; // キーワード

    public static MissionDto build(Mission mission, Tag tag){
        MissionDto dto = new MissionDto();
        dto.missionId = mission.getMissionId();
        dto.point = mission.getPoint();
        dto.title = mission.getTitle();
        dto.description = mission.getDescription();
        dto.CO2Reduction = mission.getCO2Reduction();
        dto.costReduction = mission.getCostReduction();
        dto.difficulty = mission.getDifficulty();
        dto.missionType = mission.getMissionType();
        dto.tagId = mission.getTagId();
        dto.keyword = tag.getKeyword();
        return dto;
    }
}
