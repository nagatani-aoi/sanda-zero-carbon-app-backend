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
 * 記事エンティティ
 * 
 * @author sato
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long articleId;
    String title;
    Long tagId;
    String description;
    // @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime postedAt;
    String thumbnailSource;
    Boolean isImportant;
    String url;
}
