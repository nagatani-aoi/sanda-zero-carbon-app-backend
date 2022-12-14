package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * イニシャルロケーションのエンティティ
 * 
 * @author sato
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InitialLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long initialLocationId;
    int stage;
    int initialLocation;
}
