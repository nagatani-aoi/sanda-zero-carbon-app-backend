package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import java.io.IOException;
import java.time.LocalDateTime;

import org.jsoup.nodes.Element;

import jp.kobespiral.sandazerocarbonappbackend.cofigration.Ogp;
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

    /**
     * 記事のDTOをエンティティから作成
     *
     * @param Article
     * @return ArticleDto
     */
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

    /**
     * 記事のDTOをエンティティとOGPから作成
     *
     * @param Article
     * @return ArticleDto
     */
    public static ArticleDto buildOgp(Article article) throws IOException {
        // OGPの取得版に切り替える
        ArticleDto dto = new ArticleDto();
        dto.articleId = article.getArticleId();
        // dto.title = article.getTitle();
        dto.tagId = article.getTagId();
        // dto.description = article.getDescription();
        dto.postedAt = article.getPostedAt();
        // dto.thumbnailSource = article.getThumbnailSource();
        dto.isImportant = article.getIsImportant();
        dto.url = article.getUrl();

        dto.title = Ogp.getOgp(dto.url).select("meta[property~=og:title*]").attr("content");
        dto.description = Ogp.getOgp(dto.url).select("meta[property~=og:description*]").attr("content");
        dto.thumbnailSource = Ogp.getOgp(dto.url).select("meta[property~=og:image*]").attr("content");

        // System.out.println(Ogp.getOgp(dto.url));
        // Ogp.getOgp(dto.url);

        // for (Element element : Ogp.getOgp(dto.url)) {
        // // System.out.println(element);
        // System.out.println(element.attr("property"));
        // // System.out.println(element.attr("property"));
        // // System.out.println(element.attr("content"));
        // // System.out.println(element.select("og:title"));
        // }

        // System.out.println(Ogp.getOgp(dto.url).select("meta[property~=og:title*]").attr("content"));
        System.out.println(dto);

        return dto;
    }
}
