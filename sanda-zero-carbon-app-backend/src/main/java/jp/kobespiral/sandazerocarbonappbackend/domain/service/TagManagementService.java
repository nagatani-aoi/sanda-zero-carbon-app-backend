package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.TagDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.TagForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.TagValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TagRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.TagErrorCode.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sato
 */
@Service
public class TagManagementService {
    @Autowired
    TagRepository tagRepository;

    /**
     * タグの生成
     * @param form タグフォーム
     * @return タグDto
     */
    public TagDto createTag(TagForm form){
        //もしキーワード被ってたらエラー
        if(tagRepository.existsByKeyword(form.getKeyword())){
            throw new TagValidationException(TAG_ALREADY_EXISTS,": Tag already exists",String.format("Try to create userId : %s",form.getKeyword()));
        }
        else{
            Tag tag = form.toEntity();
            tagRepository.save(tag);
            TagDto tagDto = TagDto.build(tag);
            return tagDto;
        }
    }

    /**
     * 全てのタグのタグDtoを取得する
     * @return タグDtoのリスト
     */
    public List<TagDto> getAllTag(){
        List<Tag> tags = tagRepository.findAll();
        ArrayList<TagDto> tagDtos = new ArrayList<TagDto>();
        for(Tag tag : tags){
            tagDtos.add(TagDto.build(tag));
        }
        return tagDtos;       
    }

    /**
     * タグを取得する(Dtoで)
     * @param tagId
     * @return
     */
    public TagDto getTag(Long tagId){
        Tag tag = tagRepository.findById(tagId).orElseThrow(()->new TagValidationException(TAG_DOES_NOT_EXIST,"Not exist the tag", String.format("Try to get tagId : %d",tagId)));
        return TagDto.build(tag);       
    }

    /**
     * タグの更新
     * @param form タグフォーム
     * @return 更新したタグのDto
     */
    public TagDto updateTag(TagForm form){
        Tag tag = tagRepository.findById(form.getTagId()).orElseThrow(()->new TagValidationException(TAG_DOES_NOT_EXIST,"Not exist the tag", String.format("Try to get tagId : %d",form.getTagId())));
        if(tagRepository.existsByKeyword(form.getKeyword())){
            throw new TagValidationException(TAG_ALREADY_EXISTS,": Tag already exists",String.format("Try to update userId : %s",form.getKeyword()));
        }
        tag.setKeyword(form.getKeyword());
        tagRepository.save(tag);
        return TagDto.build(tag);       
    }
    /**
     * タグの削除
     * @param tagId タグID
     * @return 消せたか(true)、消せてないか(false)
     */
    public Boolean deleteTag(Long tagId){
        try{
            tagRepository.deleteById(tagId);
            return true;
        }
        catch(Exception e){
            return false;
        }
        
    }
}
