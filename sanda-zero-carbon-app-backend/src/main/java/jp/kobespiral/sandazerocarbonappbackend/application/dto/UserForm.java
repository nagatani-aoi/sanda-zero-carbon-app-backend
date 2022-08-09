package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;
import lombok.Data;
/**
 * @author sato
 */
@Data
public class UserForm {
    String nickname;
    String password;
    int age;
    public User toEntity(){
        User user = new User(null,nickname,password,age,0);
        return user;
    }
}
