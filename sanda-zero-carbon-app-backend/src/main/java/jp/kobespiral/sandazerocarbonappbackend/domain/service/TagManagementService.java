package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.TagDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.TagForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.TagValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TagRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

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
            throw new TagValidationException(TAG_ALREADY_EXISTS,"create tag",String.format("tag-keyword : %s has already exist",form.getKeyword()));
        }
        else{
            Tag tag = form.toEntity();
            tagRepository.save(tag);
            TagDto tagDto = TagDto.build(tag);
            return tagDto;
        }
    }

    /**
     * タグを取得する(Dtoで)
     * @param tagId
     * @return
     */
    public TagDto getTag(Long tagId){
        Tag tag = tagRepository.findById(tagId).orElseThrow(()->new TagValidationException(NO_SUCH_TAG_EXISTS,"get tag", String.format("tag (Id:%d) does not exist",tagId)));
        return TagDto.build(tag);       
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
     * タグの更新
     * @param form タグフォーム
     * @return 更新したタグのDto
     */
    public TagDto updateTag(TagForm form){
        Tag tag = tagRepository.findById(form.getTagId()).orElseThrow(()->new TagValidationException(NO_SUCH_TAG_EXISTS,"update tag", String.format("tag (Id:%d) does not exist",form.getTagId())));
        if(tagRepository.existsByKeyword(form.getKeyword())){
            throw new TagValidationException(TAG_ALREADY_EXISTS,"update tag",String.format("tag-keyword : %s has already exist",form.getKeyword()));
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
