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
    Long achievementId; // 達成ID
    String userId; // ユーザID
    Long missionId; // ミッションID
    String title; // 題目
    MissionType missionType; // ミッションタイプ
    int hour; // ミッション実行時間
    int getPoint; // 獲得したポイント
    double getCO2Reduction; // 獲得したCO2削減量
    double getcostReduction; // 獲得した節約金額
    LocalDateTime achievedAt; // 達成日時
    Boolean isDailyMission; // デーリーミッション判定フラグ
    // Long tagId; // タグID

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
        dto.achievementId = achievement.getAchievementId();
        dto.userId = achievement.getUserId();
        dto.missionId = achievement.getMissionId();
        dto.getPoint = achievement.getGetPoint();
        dto.title = missionDto.getTitle();
        dto.missionType = missionDto.getMissionType();
        dto.getCO2Reduction = missionDto.getCO2Reduction() * achievement.getHour();
        dto.getcostReduction = missionDto.getCostReduction() * achievement.getHour();
        dto.achievedAt = achievement.getAchievedAt();
        dto.isDailyMission = achievement.getIsDailyMission();
        return dto;
    }
}
