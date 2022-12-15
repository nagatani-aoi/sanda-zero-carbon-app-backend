package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import java.time.LocalDateTime;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Article;
import lombok.Data;

@Data
public class ArticleDto {

    Long articleId; // 記事ID
    String title; // タイトル
    Long tagId; // タグID
    String description; // 説明
    LocalDateTime postedAt; // 投稿日時
    String thumbnailSource; // サムネイルソース
    Boolean isImportant; // 重要かどうか
    String url; // URL

    public static ArticleDto build(Article article) {
        ArticleDto dto = new ArticleDto();
        dto.articleId = article.getArticleId();
        dto.title = article.getTitle();
        dto.tagId = article.getTagId();
        dto.description = article.getDescription();
        dto.postedAt = article.getPostedAt();
        dto.thumbnailSource = article.getThumbnailSource();
        dto.isImportant = article.getIsImportant();
        dto.url = article.getUrl();
        return dto;
    }

    public static ArticleDto buildOgp(Article article) {
        // OGPの取得版に切り替える
        ArticleDto dto = new ArticleDto();
        dto.articleId = article.getArticleId();
        dto.title = article.getTitle();
        dto.tagId = article.getTagId();
        dto.description = article.getDescription();
        dto.postedAt = article.getPostedAt();
        dto.thumbnailSource = article.getThumbnailSource();
        dto.isImportant = article.getIsImportant();
        dto.url = article.getUrl();
        return dto;
    }
}
