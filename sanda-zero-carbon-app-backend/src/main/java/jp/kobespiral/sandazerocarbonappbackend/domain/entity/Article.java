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
 * @author sato checked ing
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long articleId; // 記事ID
    String title; // 記事タイトル
    Long tagId; // タグID
    @Column(length = 100000) // 最大10万文字
    String description; // 説明
    LocalDateTime postedAt; // 投稿時間
    String thumbnailSource; // サムネイル画像URL
    Boolean isImportant; // 重要かどうか
    String url; // 記事リンク
}
