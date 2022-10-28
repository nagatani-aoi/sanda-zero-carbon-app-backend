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
 * デイリーミッション エンティティ
 * 
 * @author Marin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DailyMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long dailyMissionId; // デイリーミッションID
    Long missionId; // ミッションID
    LocalDate date; // デイリーミッションの日時
}
