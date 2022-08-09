package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserDto;
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
    /**
     * ユーザ作成
     * @param form ユーザ作成フォーム
     * @return 作成したユーザエンティティ
     */
    @PostMapping("/user")
    public Response<User> createUser(@Validated @RequestBody UserForm form){
        return ResponseCreator.succeed(userService.createUser(form));
    }

    /*--------------------------Read--------------------------- */
    /**
     * ユーザログイン
     * @param userId
     * @return ログインが成功or失敗
     */
    @GetMapping("/user/login")
    public Response<Boolean> login(@RequestParam("userId") String userId,@RequestParam("password") String password){
        try{
            userService.loginUser(userId,password);
            return ResponseCreator.succeed(true);
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.USER_DOES_NOT_EXIST,e,false);
        }
    }

    /**
     * ユーザdtoを取得。レベルとか取得できる。
     * @param userId ユーザID
     * @return ユーザdto
     */
    @GetMapping("/user")
    public Response<UserDto> getUserDto(@Validated @RequestParam("userId") String userId){
        try{
            UserDto user = userService.getUserDto(userId);
            return ResponseCreator.succeed(user);
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.USER_DOES_NOT_EXIST,e, null);
        }
    }

}
