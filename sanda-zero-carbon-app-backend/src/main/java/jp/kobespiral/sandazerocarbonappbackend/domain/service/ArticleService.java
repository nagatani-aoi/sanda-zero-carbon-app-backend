package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Article;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.ArticleDto;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.ArticleRepository;
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
        for (Article list : articleList) {
            try {
                articleDtoList.add(ArticleDto.buildOgp(list));
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        return articleDtoList;
    }
}
