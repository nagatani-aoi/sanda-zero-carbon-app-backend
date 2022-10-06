package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.QuizDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.QuizForm;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.QuizValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.QuizManagementService;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

import java.util.List;
/**
 * 管理者側でクイズのCRUDを行うRESTコントローラ
 * 
 * @author kamae
 */
@RestController
@RequestMapping("/api")
public class QuizManagementRestController {
    // 管理者用クイズサービス
    @Autowired
    QuizManagementService quizManagementService;

    /*--------------Create-------------- */
    /**
     * クイズフォームを受け取り、クイズを作成する
     * 
     * @param form
     * @return 作成したクイズのDTO
     */
    @PostMapping("/sanda-admin/quiz/")
    @CrossOrigin("http://localhost:5173")
    Response<QuizDto> createQuiz(@Validated @RequestBody QuizForm form) {
        return ResponseCreator.succeed(quizManagementService.createQuiz(form));
    }

    /*-----------------Read---------------- */
    /**
     * IDで指定したクイズを取得する
     * 
     * @param quizId
     * @return 指定したクイズのDTO
     */
    @GetMapping("/sanda-admin/quiz/{quizId}")
    @CrossOrigin("http://localhost:5173")
    Response<QuizDto> getQuiz(@PathVariable Long quizId) {
        return ResponseCreator.succeed(quizManagementService.getQuiz(quizId));
    }

    /**
     * クイズをリスト形式で全て取得する
     * 
     * @return クイズのDTOリスト
     */
    @GetMapping("/sanda-admin/quiz/")
    @CrossOrigin("http://localhost:5173")
    Response<List<QuizDto>> getAllQuiz() {
        try{
            return ResponseCreator.succeed(quizManagementService.getAllQuiz());
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.QUIZ_DOES_NOT_EXIST,new QuizValidationException(QUIZ_DOES_NOT_EXIST,"update quiz", String.format("no quiz exist")),null);
        }
    }

    /*-----------------Update--------------- */
    /**
     * IDで指定したクイズの内容を更新する
     * 
     * @param form
     * @param quizId
     * @return 更新したクイズのDTO
     */
    @PutMapping("/sanda-admin/quiz/")
    @CrossOrigin("http://localhost:5173")
    Response<QuizDto> updateQuiz(@Validated @RequestBody QuizForm form, @RequestParam("quizId") Long quizId) {
        return ResponseCreator.succeed(quizManagementService.updateQuiz(quizId, form));
    }

    /*-----------------Delete---------------- */
    /**
     * IDで指定したクイズを削除する
     * 
     * @param quizId
     * @return Boolean
     */
    @DeleteMapping("/sanda-admin/quiz/")
    @CrossOrigin("http://localhost:5173")
    Response<Boolean> deleteQuiz(@RequestParam("quizId") Long quizId) {
        try{
            return ResponseCreator.succeed(quizManagementService.deleteQuiz(quizId));
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.QUIZ_DOES_NOT_EXIST,new QuizValidationException(QUIZ_DOES_NOT_EXIST,"delete quiz", String.format("quizId: %d do not exist", quizId)),null);
        }
    }
}
