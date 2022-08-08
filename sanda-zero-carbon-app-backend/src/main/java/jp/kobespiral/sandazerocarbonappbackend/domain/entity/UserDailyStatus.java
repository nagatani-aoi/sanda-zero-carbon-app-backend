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
 * @author sato
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserDailyStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userDailyStatusId;
    Long userId;
    // @Temporal(TemporalType.TIMESTAMP)
    LocalDate date;
    int totalPoint;
    double totalCO2Reduction;
    double totalCostReduction;
    Boolean easyMissionCompleted;
    Boolean normalMissionCompleted;
    Boolean hardMissionCompleted;
}
