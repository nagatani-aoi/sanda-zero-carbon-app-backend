package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserDailyDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.UserValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

/**
 * ユーザのRESTController
 * 
 * @author sato
 */
@RestController
@RequiredArgsConstructor
// @CrossOrigin("http://localhost:5173")
//@CrossOrigin("https://sanda-zero-carbon-app-yuyohi.vercel.app/")
@RequestMapping("/api")
public class UserRestController {
    /** ユーザのサービス */
    @Autowired
    UserService userService;

    /*--------------------------Create--------------------------- */
    /**
     * ユーザ作成
     * 
     * @param form ユーザ作成フォーム
     * @return 作成したユーザエンティティ
     */
    @PostMapping("/user")
    public Response<User> createUser(@Validated @RequestBody UserForm form) {
        try {
            User user = userService.createUser(form);
            return ResponseCreator.succeed(user);
        } catch (Exception e) {
            return ResponseCreator.fail(USER_ALREADY_EXISTS, new UserValidationException(USER_ALREADY_EXISTS,
                    "create user", String.format("userId : %s has already exist", form.getUserId())), null);
        }
    }

    /*--------------------------Read--------------------------- */
    /**
     * ユーザログイン
     * 
     * @param userId
     * @return ログインが成功or失敗
     */
    @GetMapping("/user/login")
    public Response<Boolean> login(@RequestParam("userId") String userId, @RequestParam("password") String password) {
        try {
            userService.loginUser(userId, password);
            return ResponseCreator.succeed(true);
        } catch (Exception e) {
            return ResponseCreator.fail(USER_DOES_NOT_EXIST, new UserValidationException(USER_DOES_NOT_EXIST,
                    "login user", String.format("userId : %s ,password %s doesn't exist.", userId, password)), false);
        }

    }

    /**
     * ユーザdtoを取得。レベルとか取得できる。
     * 
     * @param userId ユーザID
     * @return ユーザdto
     */
    @GetMapping("/user")
    public Response<UserDto> getUserDto(@Validated @RequestParam("userId") String userId) {
        try {
            UserDto user = userService.getUserDto(userId);
            return ResponseCreator.succeed(user);
        } catch (Exception e) {
            return ResponseCreator.fail(USER_DOES_NOT_EXIST, new UserValidationException(USER_DOES_NOT_EXIST,
                    "get userDto", String.format("userId : %s doesn't exits", userId)), null);
        }
    }

    /**
     * ユーザデイリーステータスDtoの取得
     * 
     * @param userId ユーザID
     * @return
     */
    @GetMapping("/user/daily")
    public Response<UserDailyDto> getUserDailyDto(@Validated @RequestParam("userId") String userId) {
        try {
            UserDailyDto dto = userService.getUserDailyDto(userId);
            return ResponseCreator.succeed(dto);
        } catch (Exception e) {
            return ResponseCreator.fail(USER_DOES_NOT_EXIST, new UserValidationException(USER_DOES_NOT_EXIST,
                    "get userDailyDto", String.format("userId : %s doesn't exits", userId)), null);
        }
    }

    /**
     * ユーザの存在の確認
     * 
     * @param userId ユーザID
     * @return ユーザが存在する(true)かしないか(false)
     */
    @GetMapping("/user/exist")
    public Response<Boolean> getUserExist(@Validated @RequestParam("userId") String userId) {
        Boolean judge = userService.isUserExist(userId);
        return ResponseCreator.succeed(judge);
    }

    /**
     * パスワードを変更する
     * 
     * @param userId   ユーザID
     * @param password パスワード
     * @return 変更されたパスワードを持つユーザエンティティ
     */
    @PostMapping("/user/changep")
    public Response<User> changeUserPassword(@Validated @RequestParam("userId") String userId,
            @Validated @RequestParam("password") String password) {
        try {
            User user = userService.changePassword(userId, password);
            return ResponseCreator.succeed(user);
        } catch (Exception e) {
            return ResponseCreator.fail(USER_DOES_NOT_EXIST, new UserValidationException(USER_DOES_NOT_EXIST,
                    "change user password", String.format("userId : %s doesn't exits", userId)), null);
        }
    }

}
