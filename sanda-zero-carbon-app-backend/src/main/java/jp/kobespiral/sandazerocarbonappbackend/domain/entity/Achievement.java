package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 達成 エンティティ
 * 
 * @author Kamae
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long achievementId; // 達成ID
    Long userId; // ユーザID
    Long missionId; // ミッションID
    int hour; // ミッション実行時間
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime achievedAt; // 達成日時
    Boolean isDailyMission; // デーリーミッション判定フラグ
}
