package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import java.io.IOException;

import javax.imageio.IIOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.UserService;
import lombok.RequiredArgsConstructor;
/**
 * ユーザのRESTController
 * 
 * @author sato
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {
    /** ユーザのサービス */
    @Autowired
    UserService userService;

    /*--------------------------Create--------------------------- */
    @PostMapping("/user")
    public Response<User> createUser(UserForm form){
        return ResponseCreator.succeed(userService.createUser(form));
    }

    /*--------------------------Read--------------------------- */
    @GetMapping("/user/login")
    public Response<Boolean> login(Long userId){
        try{
            userService.getUser(userId);
            return ResponseCreator.succeed(true);
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.USER_DOES_NOT_EXIST,e, false);
        }
    }

    @GetMapping("/user")
    public Response<User> getUser(Long userId){
        try{
            User user = userService.getUser(userId);
            return ResponseCreator.succeed(user);
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.USER_DOES_NOT_EXIST,e, null);
        }
    }

}
