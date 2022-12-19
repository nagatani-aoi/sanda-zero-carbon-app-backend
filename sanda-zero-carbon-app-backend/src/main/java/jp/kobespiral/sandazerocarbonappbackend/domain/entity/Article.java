package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 記事エンティティ
 *
 * @author sato & ing
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
    @Column(length = 100000)
    String description;
    LocalDateTime postedAt;
    String thumbnailSource;
    Boolean isImportant;
    String url;
    /** OGP機能を利用するか */
    Boolean isOgpUsed;
}
