package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.UserForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
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
        return null;
    }

}
