package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.ArticleDto;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Article;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.ArticleRepository;

/**
 * @author sato
 */

@Service
public class UserArticleService {
    @Autowired
    ArticleRepository articleRepository;
   /**
    * 全ての記事を取得する
    * @return　記事dtoリスト
    */
    public List<ArticleDto> getAllArticle(){
        List<Article> articleList = articleRepository.findAllByOrderByPostedAtDesc();
        List<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
        for (Article list : articleList) {
            articleDtoList.add(ArticleDto.build(list));
        }
        return articleDtoList;
    }

    /**
     * 最新の記事から任意の個数の記事を取得する
     * @param articleCount 取ってくる記事の個数
     * @return 記事dtoのリスト
     */
    public List<ArticleDto> getTopArticle(int articleCount){
        List<Article> articleList = articleRepository.findAllByOrderByPostedAtDesc();
        List<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
        int nowCount=0;
        for (Article list : articleList) {
            if(nowCount<articleCount){
                articleDtoList.add(ArticleDto.build(list));
                nowCount+=1;
            }
        }
        return articleDtoList;
    }


}
