package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 達成 エンティティ
 *
 * @author Kamae checked ing
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long achievementId; // 達成ID
    String userId; // ユーザID
    Long missionId; // ミッションID
    int hour; // ミッション実行時間
    int getPoint; // 獲得したポイント
    LocalDateTime achievedAt; // 達成日時
    Boolean isDailyMission; // デーリーミッション判定フラグ
}
