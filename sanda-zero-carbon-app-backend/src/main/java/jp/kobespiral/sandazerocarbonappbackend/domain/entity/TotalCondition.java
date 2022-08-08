package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 市の状況把握エンティティ
 * 
 * @author sato
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TotalCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cityConditionId;
    double costReduction;
    double CO2Reduction;
    LocalDateTime recordedAt; // 記録日時
}