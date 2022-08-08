package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;
import lombok.Data;
/**
 * @author sato
 */
@Data
public class UserForm {
    String nickname;
    int age;
    public User toEntity(){
        User user = new User(null,nickname,age,0);
        return user;
    }
}
