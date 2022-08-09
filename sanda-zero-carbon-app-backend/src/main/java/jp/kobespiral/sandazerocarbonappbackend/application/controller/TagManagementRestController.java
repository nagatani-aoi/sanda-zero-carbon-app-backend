package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.TagDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.TagForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.TagManagementService;
import lombok.RequiredArgsConstructor;

/**
 * タグのRESTController
 * 
 * @author sato
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TagManagementRestController {
    /** タグのサービス */
    @Autowired
    TagManagementService tagService;

    /*--------------------------Create--------------------------- */
    /**
     * タグ作成
     * @param form タグフォーム
     * @return 作成の成功か、失敗
     */
    @PostMapping("/sanda-admin/tag")
    public Response<Boolean> tagUser(@Validated @RequestBody TagForm form){
        try{
            tagService.createTag(form);
            return ResponseCreator.succeed(true);
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.TAG_DOES_NOT_EXIST,e,false);
        }
    }

    /*--------------------------Read--------------------------- */
    /**
     * タグの取得
     * @param tagId タグID
     * @return タグDto
     */
    @GetMapping("/sanda-admin/{tagId}")
    public Response<TagDto> getTag(@PathVariable Long tagId){
        try{
            TagDto tagDto = tagService.getTag(tagId);
            return ResponseCreator.succeed(tagDto);
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.TAG_DOES_NOT_EXIST,e,null);
        }
    }

    /**
     * 全てのタグを取得する
     * @return タグDtoのリスト
     */
    @GetMapping("/sanda-admin/tag")
    public Response<List<TagDto>> getAllTag(){
        try{
            List<TagDto> tagDtos = tagService.getAllTag();
            return ResponseCreator.succeed(tagDtos);
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.TAG_DOES_NOT_EXIST,e,null);
        }
    }
    /*--------------------------Update--------------------------- */
    /**
     * タグの更新
     * @param form 更新するための情報が入ったフォーム
     * @return 成功or失敗
     */
    @PutMapping("/sanda-admin/tag")
    public Response<Boolean> updateTag(@Validated @RequestBody TagForm form){
        try{
            tagService.updateTag(form);
            return ResponseCreator.succeed(true);
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.TAG_DOES_NOT_EXIST,e,false);
        }
    }

    /*--------------------------Delete--------------------------- */
    /**
     * タグの削除
     * @param tagId タグID
     * @return 成功or失敗
     */
    @DeleteMapping("/sanda-admin/tag")
    public Response<Boolean> deleteTag(@Validated @RequestParam("tagId") Long tagId){
        if(tagService.deleteTag(tagId)){
            return ResponseCreator.succeed(true);
        }
        else {
            return ResponseCreator.fail(ErrorCode.TAG_DOES_NOT_EXIST,null,false);
        }
    }
}
