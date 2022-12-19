package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ArticleValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.MissionValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.QuizValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.TagValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.TotalConditionException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.UserValidationException;

/**
 * エラーハンドラー
 * 
 * @author kamae
 */
@RestControllerAdvice
@RequestMapping("/api")
public class ErrorHandler {
    /**
     * Mapping()必要？
     * Reponse<?>
     * AchievementRestControllerのgetTotalParamで検証
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(UserValidationException.class)
    public Response<Object> handleUserValidationException(UserValidationException ex) {
        return ResponseCreator.fail(ex.getCode(), ex, null);
    }

    @ExceptionHandler(TagValidationException.class)
    public Response<Object> handleTagValidationException(TagValidationException ex) {
        return ResponseCreator.fail(ex.getCode(), ex, null);
    }

    @ExceptionHandler(MissionValidationException.class)
    public Response<Object> handleMissionValidationException(MissionValidationException ex) {
        return ResponseCreator.fail(ex.getCode(), ex, null);
    }

    @ExceptionHandler(QuizValidationException.class)
    public Response<Object> handleQuizValidationException(QuizValidationException ex) {
        return ResponseCreator.fail(ex.getCode(), ex, null);
    }

    @ExceptionHandler(ArticleValidationException.class)
    public Response<Object> handleArticleValidationException(ArticleValidationException ex) {
        return ResponseCreator.fail(ex.getCode(), ex, null);
    }

    @ExceptionHandler(TotalConditionException.class)
    public Response<Object> handleTotalConditionException(TotalConditionException ex) {
        return ResponseCreator.fail(ex.getCode(), ex, null);
    }
}
