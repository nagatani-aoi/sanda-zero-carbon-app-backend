package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.AnsweredQuizDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.AnsweredQuizForm;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.CorrectQuizCountDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.QuizDto;

import java.util.List;

import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.QuizService;

/**
 * ユーザ側でクイズに対する操作（クイズに回答、クイズリストの取得）を行うRESTコントローラ
 * 
 * @author kamae
 */
@RestController
@RequestMapping("/api")
public class QuizRestController {
    // クイズサービス
    @Autowired
    QuizService quizService;

    /*--------------Create------------- */
    /**
     * クイズに回答する
     * 
     * @param form
     * @return 回答したクイズのDTO
     */
    @PostMapping("/quiz/answer")
    Response<AnsweredQuizDto> answerQuiz(@Validated @RequestBody AnsweredQuizForm form) {
        return ResponseCreator.succeed(quizService.createAnsweredQuiz(form));
    }

    /*--------------Read--------------- */
    /**
     * 未回答のクイズをリストで取得する
     * 
     * @param userId
     * @return クイズのDTOリスト
     */
    @GetMapping("/quiz/unanswer")
    Response<List<QuizDto>> getUnansweredQuiz(@RequestParam("userId") String userId) {
        return ResponseCreator.succeed(quizService.getUnansweredQuiz(userId));
    }

    /**
     * 正解したクイズをリストで取得する
     * 
     * @param userId
     * @return クイズのDTOリスト
     */
    @GetMapping("/quiz/correct")
    Response<List<QuizDto>> getCorrectAnsweredQuiz(@RequestParam("userId") String userId) {
        return ResponseCreator.succeed(quizService.getCorrectAnsweredQuiz(userId));
    }

    /**
     * 不正解のクイズをリストで取得する
     * 
     * @param userId
     * @return クイズのDTOリスト
     */
    @GetMapping("/quiz/incorrect")
    Response<List<QuizDto>> getIncorrectAnsweredQuiz(@RequestParam("userId") String userId) {
        return ResponseCreator.succeed(quizService.getIncorrectAnsweredQuiz(userId));
    }
    
    /**
     * クイズの総数と正解数を取得する
     * 
     * @param userId
     * @return CorrectQuizCountDto
     */
    @GetMapping("/quiz/status")
    Response<CorrectQuizCountDto> getCorrectQuizCount(@RequestParam("userId") String userId) {
        return ResponseCreator.succeed(quizService.getCorrectQuizCount(userId));
    }
}
