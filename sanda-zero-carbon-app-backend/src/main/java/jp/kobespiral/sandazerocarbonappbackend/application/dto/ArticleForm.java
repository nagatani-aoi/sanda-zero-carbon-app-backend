package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Article;
import lombok.Data;

/**
 * 記事の投稿フォーム
 *
 * @author sato & ing
 */
@Data
public class ArticleForm {
    @NotBlank
    String url;
    @NotNull
    Boolean isImportant;
    @NotNull
    Long tagId;
    @NotNull
    Boolean isOgpUsed;
    String title;
    String description;
    String thumbnailSource;

    /** OGP機能を利用 */

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
