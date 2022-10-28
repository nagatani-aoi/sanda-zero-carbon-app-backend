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
 * 市の状況把握エンティティ
 *
 * @author sato checked ing
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TotalCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long totalConditionId; // 市の状況ID
    double costReduction; // 節約金額
    double co2Reduction; // 削減CO2
    LocalDateTime since; // いつから
    LocalDateTime until; // いつまで
    LocalDateTime recordedAt; // 記録日時
}