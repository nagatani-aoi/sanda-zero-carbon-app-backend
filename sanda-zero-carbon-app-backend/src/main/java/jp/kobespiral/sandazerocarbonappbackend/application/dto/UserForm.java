package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import javax.validation.constraints.NotBlank;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;
import lombok.Data;
/**
 * @author sato
 */
@Data
public class UserForm {
    @NotBlank
    String userId;
    String password;
    int age;
    public User toEntity(){
        User user = new User(userId,password,age,0);
        return user;
    }
}
