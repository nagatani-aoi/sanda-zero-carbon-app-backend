package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Article;
import lombok.Data;

/**
 * @author sato
 */
@Data
public class ArticleForm {
    String title;
    Long tagId;
    String description;
    String thumbnailSource;
    Boolean isImportant;
    String url;

    public Article toEntity(){
        Article article = new Article(null,title,tagId,description,null,thumbnailSource,isImportant,url);
        return article;
    }
}
