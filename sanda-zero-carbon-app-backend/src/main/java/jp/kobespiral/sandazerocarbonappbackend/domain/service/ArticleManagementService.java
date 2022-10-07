package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.ArticleDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.ArticleForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ArticleValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Article;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.ArticleRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

/**
 * 記事の管理者サービス
 * 
 * @author sato
 */

@Service
public class ArticleManagementService {
    @Autowired
    ArticleRepository articleRepository;

    /**
     * 記事を作成する
     * @param form 記事フォーム
     * @return 作成した記事のdto
     */
    public ArticleDto createArticle(ArticleForm form){

        Article article = form.toEntity();
        article.setPostedAt(LocalDateTime.now()); // 現在時間をセット
        articleRepository.save(article);
        ArticleDto articleDto = new ArticleDto();
        articleDto = ArticleDto.build(article);
        return articleDto;
    }

    /**
    * 全ての記事を取得する
    * @return　記事dtoリスト
    */
    public List<ArticleDto> getAllArticles(){
        List<Article> articleList = articleRepository.findAllByOrderByPostedAtDesc();
        List<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
        for (Article list : articleList) {
            articleDtoList.add(ArticleDto.build(list));
        }
        return articleDtoList;
    }

    /**
     * 記事を取得する
     * @param articleId 記事ID
     * @return 記事dto
     */
    public ArticleDto getArticle(Long articleId) {

        //////////////////////////////エラーのところはコンフリ注意して後で変更
        Article article = articleRepository.findById(articleId).orElseThrow(()->new ArticleValidationException(USER_DOES_NOT_EXIST,"get article", String.format("articleId : %s does't exist",articleId)));
        ArticleDto articleDto = new ArticleDto();
        articleDto = ArticleDto.build(article);
        return articleDto;
    }

    /**
     * 記事を更新する
     * @param articleId 記事ID
     * @param form 記事フォーム
     * @return 更新後の記事dto
     */
    public ArticleDto updateArticle(Long articleId,ArticleForm form){
        Article article = articleRepository.findById(articleId).orElseThrow(()->new ArticleValidationException(USER_DOES_NOT_EXIST,"update article", String.format("userId : %s doesn't exist",articleId)));
        article.setTitle(form.getTitle());
        article.setTagId(form.getTagId());
        article.setDescription(form.getDescription());
        //時間は現在時刻に更新
        article.setPostedAt(LocalDateTime.now());
        article.setThumbnailSource(form.getThumbnailSource());
        article.setIsImportant(form.getIsImportant());
        article.setUrl(form.getUrl());
        articleRepository.save(article);
        ArticleDto articleDto = new ArticleDto();
        articleDto = ArticleDto.build(article);
        return articleDto;
    }

    /**
     * 記事を削除する
     * @param articleId 記事ID
     * @return 削除の成功or失敗
     */
    public boolean deleteArticle(Long articleId){
        if(articleRepository.existsById(articleId)){
            articleRepository.deleteById(articleId);
            
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * タグIDで記事を検索する
     * @param tagId タグID
     * @return 記事dtoリスト
     */
    public List<ArticleDto> searchArticleByTagId(Long tagId){
        List<Article> articleList = articleRepository.findByTagIdOrderByPostedAtDesc(tagId);
        List<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
        for (Article list : articleList) {
            articleDtoList.add(ArticleDto.build(list));
        }
        return articleDtoList;
    }

    /**
     * 重要度の高い記事を検索する
     * @return 記事dtoリスト
     */
    public List<ArticleDto> searchArticleByIsImportant(){
        List<Article> articleList = articleRepository.findByIsImportantTrue();
        List<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
        for (Article list : articleList) {
            articleDtoList.add(ArticleDto.build(list));
        }
        return articleDtoList;
    }

    /**
     * キーワードでタイトルを検索する
     * @param keyword キーワード
     * @return 記事dtoリスト
     */
    public List<ArticleDto> searchArticleByKeyword(String keyword){
        List<Article> articleList = articleRepository.findByTitleContaining(keyword);
        List<ArticleDto> articleDtoList = new ArrayList<ArticleDto>();
        for (Article list : articleList) {
            articleDtoList.add(ArticleDto.build(list));
        }
        return articleDtoList;
    }
}
