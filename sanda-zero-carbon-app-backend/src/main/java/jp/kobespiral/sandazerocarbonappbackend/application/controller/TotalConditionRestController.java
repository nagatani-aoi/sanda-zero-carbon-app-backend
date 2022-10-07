package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.TotalConditionDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.TotalConditionValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.TotalConditionService;

import lombok.RequiredArgsConstructor;

/**
 * 市の状況のRESTコントローラー
 * 
 * @author ing
 */
// @CrossOrigin("http://localhost:5173")
@CrossOrigin("https://sanda-zero-carbon-app-yuyohi.vercel.app/")
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class TotalConditionRestController {
    /** 市の状況のサービス */
    private final TotalConditionService totalConditionService;

    /* -------------------- Create -------------------- */

    /**
     * 直近1時間の市の状況を計算する
     *
     * @return Response<boolean>
     */
    @PostMapping("/total-condition")
    public Response<Boolean> calculateTotalCondition() {
        try {
            totalConditionService.calculateTotalCondition(); // 直近1時間の市の状況を計算
            return ResponseCreator.succeed(true); // trueを返す
        } catch (TotalConditionValidationException e) {
            return ResponseCreator.fail(ErrorCode.TOTAL_CONDITION_COULD_NOT_BE_CALUCULATE, e, false); // 例外をキャッチしてfalseを返す
        } catch (Exception e) {
            return ResponseCreator.fail(ErrorCode.OTHER_ERROR, e, false); // 例外をキャッチしてfalseを返す
        }
    }

    /* -------------------- Read -------------------- */

    /**
     * 全ての市の状況を集計して取得
     *
     * @return Response<市の状況DTO>
     */
    @GetMapping("/total-condition")
    public Response<TotalConditionDto> getAllTotalCondition() {
        try {
            return ResponseCreator.succeed(totalConditionService.getAllTotalCondition()); // 全ての市の状況を集計して返す
        } catch (Exception e) {
            return ResponseCreator.fail(ErrorCode.OTHER_ERROR, e, null); // 例外をキャッチしてfalseを返す
        }
    }

    /**
     * 直近1週間の市の状況を集計して取得
     *
     * @return Response<市の状況DTO>
     */
    @GetMapping("/total-condition/weekly")
    public Response<TotalConditionDto> getWeeklyTotalCondition() {
        try {
            return ResponseCreator.succeed(totalConditionService.getWeeklyTotalCondition()); // 全ての市の状況を集計して返す
        } catch (Exception e) {
            return ResponseCreator.fail(ErrorCode.OTHER_ERROR, e, null); // 例外をキャッチしてfalseを返す
        }
    }
}
