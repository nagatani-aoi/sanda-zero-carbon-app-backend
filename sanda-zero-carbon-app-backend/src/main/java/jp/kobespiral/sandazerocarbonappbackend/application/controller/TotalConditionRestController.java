package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.TotalConditionValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.TotalConditionService;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;

/**
 * 市の状況のRESTコントローラー
 */
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class TotalConditionRestController {
    /** 市の状況のサービス */
    private final TotalConditionService totalConditionService;

    /**
     * 直近1時間の市の状況を計算する
     *
     * @return boolean
     */
    @PostMapping("/total-codition")
    public Response<Boolean> calculateTotalCondition() {
        try {
            totalConditionService.calculateTotalCondition(); // 直近1時間の市の状況を計算
            return ResponseCreator.succeed(true); // trueを返す
        } catch (Exception e) {
            return ResponseCreator.fail(ErrorCode.TOTAL_CONDITION_COULD_NOT_BE_CALUCULATE,
                    new TotalConditionValidationException(
                            TOTAL_CONDITION_COULD_NOT_BE_CALUCULATE, "caliculate total condition",
                            String.format("Accessed a non-existent mission")),
                    false); // 例外をキャッチしてfalseを返す
        }
    }
}
