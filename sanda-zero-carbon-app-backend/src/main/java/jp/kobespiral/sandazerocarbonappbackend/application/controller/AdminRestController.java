package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;

import jp.kobespiral.sandazerocarbonappbackend.domain.service.AdministratorService;
import lombok.RequiredArgsConstructor;
/**
 * ユーザのRESTController
 * 
 * @author sato
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminRestController {
    /** 管理者のサービス */
    @Autowired
    AdministratorService adminService;

    /*--------------------------Read--------------------------- */
    /**
     * 管理者ログイン
     */

     /**
      * 管理者ログイン
      * @param administratorId 管理者ID
      * @param password パスワード
      * @return 成功or失敗
      */
    @GetMapping("/sanda-admin/login")
    public Response<Boolean> login(@RequestParam("administratorId") Long administratorId,@RequestParam("password") String password){
        try{
            adminService.getAdministrator(administratorId,password);
            return ResponseCreator.succeed(true);
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.USER_DOES_NOT_EXIST,e, false);
        }
    }


}
