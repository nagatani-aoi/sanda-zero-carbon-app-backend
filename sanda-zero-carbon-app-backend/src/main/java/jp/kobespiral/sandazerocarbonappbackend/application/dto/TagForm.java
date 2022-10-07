package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import javax.validation.constraints.NotBlank;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Tag;
import lombok.Data;
/**
 * @author sato
 */
@Data
public class TagForm {
    Long tagId;
    @NotBlank
    String keyword;
    public Tag toEntity(){
        Tag tag = new Tag(null,keyword);
        return tag;
    }
}
