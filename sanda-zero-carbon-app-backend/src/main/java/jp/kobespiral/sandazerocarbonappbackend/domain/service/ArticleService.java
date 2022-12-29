package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Article;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.ArticleDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ArticleValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.ArticleRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;

/**
 * 記事のサービス
 * 
 * @author ing
 */

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    /**
     * 全ての記事を取得する
     *
     * @return 記事dtoリスト
     */
    public List<ArticleDto> getAllArticles() {
        List<Article> articleList = articleRepository.findAllByOrderByPostedAtDesc();
        List<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
        for (Article article : articleList) {
            if (article.getIsOgpUsed()) { // OGPを利用するフラグがTrueなら
                try {
                    articleDtoList.add(ArticleDto.buildOgp(article));
                } catch (IOException e) {
                    throw new ArticleValidationException(OTHER_ERROR, "get article",
                            String.format("URL is anomalous value"));
                }
            } else {
                articleDtoList.add(ArticleDto.build(article));
            }
        }
        return articleDtoList;
    }

    /**
     * 重要度の高い記事を検索する
     * 
     * @return 記事dtoリスト
     */
    public List<ArticleDto> searchArticleByIsImportant() {
        List<Article> articleList = articleRepository.findByIsImportantTrueOrderByPostedAtDesc();
        List<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
        for (Article article : articleList) {
            if (article.getIsOgpUsed()) { // OGPを利用するフラグがTrueなら
                try {
                    articleDtoList.add(ArticleDto.buildOgp(article));
                } catch (IOException e) {
                    throw new ArticleValidationException(OTHER_ERROR, "get article",
                            String.format("URL is anomalous value"));
                }
            } else {
                articleDtoList.add(ArticleDto.build(article));
            }
        }
        return articleDtoList;
    }
}
