package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Achievement;
import lombok.Data;

/**
 * ミッション達成フォーム
 *
 * @author ing
 */
@Data
public class MissionAchieveForm {
    Long missionId; // ミッションID
    String userId; // ユーザID
    int hour; // ミッション実行時間
    Boolean isDailyMission; // デイリーミッション判定フラグ

    /**
     * 達成フォームからエンティティを作成
     *
     * @return 達成
     */
    public Achievement toEntity() {
        Achievement achievement = new Achievement();
        achievement.setMissionId(this.missionId);
        achievement.setUserId(this.userId);
        if (this.hour == 0) { // 時間がnull(0)ならば
            achievement.setHour(1); // 最小単位の時間とみなして１時間とする
        } else {
            achievement.setHour(this.hour);
        }
        achievement.setIsDailyMission(this.isDailyMission);
        return achievement;
    }
}
