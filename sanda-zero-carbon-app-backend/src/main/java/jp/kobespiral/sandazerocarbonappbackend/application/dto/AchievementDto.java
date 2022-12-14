package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import java.time.LocalDateTime;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Achievement;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.MissionType;
import lombok.Data;

/**
 * 達成のDTO
 *
 * @author ing
 */
@Data
public class AchievementDto {
    String title; // 題目
    MissionType missionType; // ミッションタイプ
    int hour; // ミッション実行時間
    int getPoint; // 獲得したポイント
    double getCo2Reduction; // 獲得したCO2削減量
    double getcostReduction; // 獲得した節約金額
    LocalDateTime achievedAt; // 達成日時
    Boolean isDailyMission; // デーリーミッション判定フラグ

    /**
     * 達成とミッションのエンティティからDtoを作成
     *
     * @param achivement 達成
     * @param mission    達成に紐づくミッション
     * @return 達成のDTO
     */
    public static AchievementDto build(Achievement achievement, MissionDto missionDto) {
        // 達成Dtoに各変数をセット
        AchievementDto dto = new AchievementDto();

        dto.getPoint = achievement.getGetPoint();
        dto.title = missionDto.getTitle();
        dto.missionType = missionDto.getMissionType();
        dto.hour = achievement.getHour();
        dto.getCo2Reduction = missionDto.getCo2Reduction() * achievement.getHour();
        dto.getcostReduction = missionDto.getCostReduction() * achievement.getHour();
        dto.achievedAt = achievement.getAchievedAt();
        dto.isDailyMission = achievement.getIsDailyMission();
        return dto;
    }
}
