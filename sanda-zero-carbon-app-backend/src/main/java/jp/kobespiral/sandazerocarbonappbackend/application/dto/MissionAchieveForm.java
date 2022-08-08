package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Achievement;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.MissionType;
import lombok.Data;

/**
 * ミッション達成フォーム
 *
 * @author ing
 */
@Data
public class MissionAchieveForm {
    Long missionId; // ミッションID
    Long userId; // ユーザID
    MissionType missionType; // ミッションタイプ
    int hour; // ミッション実行時間
    Boolean isDailyMission; // デーリーミッション判定フラグ

    /**
     * 達成フォームからエンティティを作成
     *
     * @return 達成
     */
    public Achievement toEntity() {
        Achievement achievement = new Achievement();
        achievement.setMissionId(this.missionId);
        achievement.setUserId(this.userId);
        achievement.setMissionType(this.missionType);
        achievement.setHour(this.hour);
        achievement.setIsDailyMission(this.isDailyMission);
        return null;
    }
}
