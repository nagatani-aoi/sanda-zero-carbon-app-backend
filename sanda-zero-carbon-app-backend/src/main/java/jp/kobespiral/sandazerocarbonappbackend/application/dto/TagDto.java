package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import lombok.Data;
/**
 * @author sato
 */
@Data
public class TagDto {
    Long tagId; // タグID
    String keyword; // キーワード
    public static TagDto build(Tag tag){
        TagDto dto = new TagDto();
        dto.tagId = tag.getTagId();
        dto.keyword = tag.getKeyword();
        return dto;
    }
}
