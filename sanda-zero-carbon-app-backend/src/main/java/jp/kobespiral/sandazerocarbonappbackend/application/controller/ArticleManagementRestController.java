package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.ArticleDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.ArticleForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ArticleValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.ArticleManagementService;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.UserArticleService;
import lombok.RequiredArgsConstructor;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

import java.util.List;

/**
 * 記事管理のRESTController
 * 
 * @author sato
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleManagementRestController {
    /** 記事の管理者のサービス */
    @Autowired
    ArticleManagementService articleManagementService;
    @Autowired
    UserArticleService userArticleService;

    /*--------------------------Create--------------------------- */
    /**
     * 記事を作成する
     * @param form 記事フォーム
     * @return 成功or失敗
     */
    @PostMapping("/sanda-admin/article")
    @CrossOrigin("http://localhost:5173")
    public Response<Boolean> createArticle(@Validated @RequestBody ArticleForm form){
        //作成失敗は可能性ない
        //ArticleDto articleDto = articleManagementService.createArticle(form);
        articleManagementService.createArticle(form);
        return ResponseCreator.succeed(true);
    }

    /*--------------------------Read--------------------------- */
    /**
     * 全ての記事を取得する
     * @return 記事dtoのリスト
     */
    @GetMapping("/sanda-admin/article")
    @CrossOrigin("http://localhost:5173")
    Response<List<ArticleDto>> getAllArticle(){
        try{
            return ResponseCreator.succeed(articleManagementService.getAllArticles());
        }
        catch(Exception e){
            return ResponseCreator.fail(MISSION_DOES_NOT_EXIST,new ArticleValidationException(MISSION_DOES_NOT_EXIST,"get all article", String.format("article does not exist")),null);
        }
    }

    /**
     * 最新の記事を任意の個数取得する
     * @return 記事dtoのリスト
     */
    @GetMapping("/sanda-admin/article/top")
    @CrossOrigin("http://localhost:5173")
    Response<List<ArticleDto>> getTopArticle(@Validated @RequestParam("articleCount") int articleCount){
        try{
            return ResponseCreator.succeed(userArticleService.getTopArticle(articleCount));
        }
        catch(Exception e){

            ///////////エラー処理は後で
            return ResponseCreator.fail(MISSION_DOES_NOT_EXIST,new ArticleValidationException(MISSION_DOES_NOT_EXIST,"get top article", String.format("article does not exist")),null);
        }
    }
    /**
     * 記事IDを指定して記事を取得する
     * @param articleId 記事ID
     * @return　記事dto
     */
    @GetMapping("/sanda-admin/article/article")
    @CrossOrigin("http://localhost:5173")
    Response<ArticleDto> searchArticleByArticleId(@RequestParam("articleId") Long articleId){
        try{
            return ResponseCreator.succeed(articleManagementService.getArticle(articleId));
        }
        catch(Exception e){
            ///////////エラー処理は後で
            return ResponseCreator.fail(TAG_DOES_NOT_EXIST,new ArticleValidationException(TAG_DOES_NOT_EXIST,"get article by articleId", String.format("this articleId does not exist (articleId: %d )",articleId)),null);
        }
    }

    /**
     * タグIDで記事を検索する
     * @param tagId タグID
     * @return 記事dtoのリスト
     */
    @GetMapping("/sanda-admin/article/tag")
    @CrossOrigin("http://localhost:5173")
    Response<List<ArticleDto>> searchArticleByTagId(@RequestParam("tagId") Long tagId){
        try{
            return ResponseCreator.succeed(articleManagementService.searchArticleByTagId(tagId));
        }
        catch(Exception e){
            ///////////エラー処理は後で
            return ResponseCreator.fail(TAG_DOES_NOT_EXIST,new ArticleValidationException(TAG_DOES_NOT_EXIST,"get article by tagId", String.format("this article does not exist (tagId: %d )",tagId)),null);
        }
    }

    /**
     * 重要フラグの指定されている記事を取得する
     * @return 記事dtoのリスト
     */
    @GetMapping("/sanda-admin/article/important")
    @CrossOrigin("http://localhost:5173")
    Response<List<ArticleDto>> searchArticleByIsImportant(){
        try{
            return ResponseCreator.succeed(articleManagementService.searchArticleByIsImportant());
        }
        catch(Exception e){
            ///////////エラー処理は後で
            return ResponseCreator.fail(TAG_DOES_NOT_EXIST,new ArticleValidationException(TAG_DOES_NOT_EXIST,"get article by isImportant", "this article does not exist"),null);
        }
    }

    /**
     * キーワードで記事を取得する
     * @param keyword キーワード
     * @return 記事dtoのリスト
     */
    @GetMapping("/sanda-admin/article/keyword")
    @CrossOrigin("http://localhost:5173")
    Response<List<ArticleDto>> searchArticleByKeyword(@RequestParam("keyword") String keyword){
        try{
            return ResponseCreator.succeed(articleManagementService.searchArticleByKeyword(keyword));
        }
        catch(Exception e){
            ///////////エラー処理は後で
            return ResponseCreator.fail(TAG_DOES_NOT_EXIST,new ArticleValidationException(TAG_DOES_NOT_EXIST,"get article by keyword", String.format("this article does not exist (keyword: %d )",keyword)),null);
        }
    }
    /*--------------------------Update--------------------------- */
    /**
     * 記事を更新する
     * @param articleId 記事ID
     * @param form 記事フォーム
     * @return 成功or失敗
     */
    @PutMapping("/sanda-admin/article")
    @CrossOrigin("http://localhost:5173")
    Response<Boolean> updateArticle(@RequestParam("articleId") Long articleId, @Validated @RequestBody ArticleForm form){
        try{
            articleManagementService.updateArticle(articleId, form);
            return ResponseCreator.succeed(true);
        }
        catch(Exception e){
            ///////////エラー処理は後で
            return ResponseCreator.fail(MISSION_DOES_NOT_EXIST,new ArticleValidationException(MISSION_DOES_NOT_EXIST,"update the mission","this article does not exist"),false);
        }
    }

    /*--------------------------Delete--------------------------- */
    /**
     * 記事を削除する
     * @param articleId 記事ID
     * @return 成功or失敗
     */
    @DeleteMapping("/sanda-admin/article")
    @CrossOrigin("http://localhost:5173")
    Response<Boolean> deleteArticle(@RequestParam("articleId") Long articleId){
        try{
            return ResponseCreator.succeed(articleManagementService.deleteArticle(articleId));
        }
        catch(Exception e){
            ///////////エラー処理は後で
            return ResponseCreator.fail(MISSION_DOES_NOT_EXIST,new ArticleValidationException(MISSION_DOES_NOT_EXIST,"update the mission","this article does not exist"),false);
        }
    }
}
