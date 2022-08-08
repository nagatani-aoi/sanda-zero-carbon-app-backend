package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;
import lombok.Data;

@Data
public class UserForm {
    Long userId;
    String nickname;
    int age;
    public User toEntity(){
        User user = new User(userId,nickname,age,0);
        return user;
    }
}
