package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import java.time.LocalDate;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.DailyMission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Difficulty;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.MissionType;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import lombok.Data;

@Data
public class DailyMissionDto {
    Long dailyMissionId; // デイリーミッションID
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
    LocalDate date; // デイリーミッションの日時

    public static DailyMissionDto build(Mission mission, DailyMission dailyMission, Tag tag) {
        DailyMissionDto dto = new DailyMissionDto();
        dto.dailyMissionId = dailyMission.getDailyMissionId();
        dto.missionId = mission.getMissionId();
        // デイリーミッションのポイントを2倍に
        dto.point = 2 * mission.getPoint();
        dto.title = mission.getTitle();
        dto.description = mission.getDescription();
        dto.CO2Reduction = mission.getCo2Reduction();
        dto.costReduction = mission.getCostReduction();
        dto.difficulty = mission.getDifficulty();
        dto.missionType = mission.getMissionType();
        dto.tagId = mission.getTagId();
        dto.keyword = tag.getKeyword();
        dto.date = dailyMission.getDate();
        return dto;
    }
}
