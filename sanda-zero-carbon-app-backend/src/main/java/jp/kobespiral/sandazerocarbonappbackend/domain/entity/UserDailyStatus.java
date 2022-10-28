package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザデイリーステータスエンティティ
 *
 * @author sato checked ing
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserDailyStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userDailyStatusId; // ユーザデイリーステータスID
    String userId; // ユーザID
    LocalDate date; // 日付
    int totalMissionPoint; // 今日のミッションで獲得したポイント
    int totalQuizPoint; // 今日のクイズで獲得したポイント
    double totalCo2Reduction; // CO2削減量
    double totalCostReduction; // コスト削減量
    Boolean easyMissionCompleted; // 簡単デイリーミッションの達成フラグ
    Boolean normalMissionCompleted; // 普通デイリーミッションの達成フラグ
    Boolean hardMissionCompleted; // 難しいデイリーミッションの達成フラグ
}
