package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Difficulty;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.MissionPoint;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.MissionType;
import lombok.Data;

@Data
public class MissionForm {
    // @NotNull
    // @Positive
    // @Range(min=1,max=100)
    // int point; // ポイント
    @NotBlank
    @Length(min = 1, max = 100)
    String title; // 題目
    @NotBlank
    @Length(min = 1, max = 1000)
    String description; // 説明
    @NotNull
    @PositiveOrZero
    double co2Reduction; // CO2削減量
    @NotNull
    @PositiveOrZero
    double costReduction; // 節約金額
    @NotNull
    Difficulty difficulty; // 難易度
    @NotNull
    MissionType missionType; // ミッションタイプ
    @NotNull
    Long tagId; // タグID

    public Mission toEntity() {
        Mission mission = new Mission();
        // mission.setPoint(3);
        mission.setPoint(MissionPoint.valueOf(difficulty.toString()).getInt());
        mission.setTitle(title);
        mission.setDescription(description);
        mission.setCo2Reduction(co2Reduction);
        mission.setCostReduction(costReduction);
        mission.setDifficulty(difficulty);
        mission.setMissionType(missionType);
        mission.setTagId(tagId);
        return mission;
    }
}
