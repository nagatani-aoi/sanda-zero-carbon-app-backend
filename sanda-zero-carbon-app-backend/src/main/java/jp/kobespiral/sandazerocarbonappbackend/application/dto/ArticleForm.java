package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Article;
import lombok.Data;

/**
 * 記事の投稿フォーム
 *
 * @author sato & ing
 */
@Data
public class ArticleForm {
    String title;
    Long tagId;
    String description;
    String thumbnailSource;
    Boolean isImportant;
    String url;
    /** OGP機能を利用 */
    Boolean isOgpUsed;

    /**
     * 記事フォームから記事エンティティを作成
     *
     * @return Article
     */
    public Article toEntity() {
        Article article = new Article(null, title, tagId, description, null, thumbnailSource, isImportant, url,
                isOgpUsed);
        return article;
    }
}
