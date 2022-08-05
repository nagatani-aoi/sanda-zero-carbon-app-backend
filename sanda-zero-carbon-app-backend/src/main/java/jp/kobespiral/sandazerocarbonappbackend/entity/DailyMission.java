package jp.kobespiral.sandazerocarbonappbackend.entity;

import java.util.Date;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long missionId; // ミッションID
    @Temporal(TemporalType.TIMESTAMP)
    Date date; // デイリーミッションの日時
}
