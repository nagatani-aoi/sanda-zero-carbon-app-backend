package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.AchievementDto;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.MissionAchieveForm;
import jp.kobespiral.sandazerocarbonappbackend.application.dto.TotalParamDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.MissionValidationException;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.UserValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.AchievementService;
import lombok.RequiredArgsConstructor;

import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 達成のRESTController
 *
 * @author ing
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api")
public class AchievementRestController {
    
    /** 達成のサービス */
    private final AchievementService achievementService;

    /* -------------------- Create -------------------- */

    /**
     * ミッションを達成する
     *
     * @param form ミッション達成フォーム
     * @return 達成Dto
     */
    @PostMapping("/mission/achieve")
    @CrossOrigin("http://localhost:5173")
    public Response<AchievementDto> achieveMission(@Validated @RequestBody MissionAchieveForm form) {
        try{
            return ResponseCreator.succeed(achievementService.achieveMission(form));
        }catch(UserValidationException e){
            return ResponseCreator.fail(ErrorCode.USER_DOES_NOT_EXIST,
                    new UserValidationException(USER_DOES_NOT_EXIST, "achieve mission",String.format("this user does not exist (userId: %d )", form.getUserId())),null);
        }
        catch(Exception e){
            return ResponseCreator.fail(ErrorCode.MISSION_DOES_NOT_EXIST,new MissionValidationException(MISSION_DOES_NOT_EXIST,"achieve mission", String.format("this mission does not exist (missionId: )", form.getMissionId())),null);
        }
    }

    /* -------------------- Read -------------------- */

    /**
     * 指定したユーザ，週の1週間分の達成リストを取得
     *
     * @param userId     ユーザーID
     * @param dateString String型の日付
     * @return ラップされた達成Dtoのリスト
     */
    @GetMapping("/achievement/weekly")
    public Response<List<AchievementDto>> getWeeklyAchievements(@RequestParam("userId") String userId,
            @RequestParam("date") String dateString) {
        try {
            return ResponseCreator.succeed(achievementService.getAchivement(userId, dateString)); // 達成リストを取得
        } catch (Exception e) {
            return ResponseCreator.fail(ErrorCode.USER_DOES_NOT_EXIST,
                    new UserValidationException(USER_DOES_NOT_EXIST, "get the list of weekly achievement",
                            String.format("this user does not exist (userId: %d )", userId)),
                    null);
        }
    }

    /**
     * 指定したユーザーの累計パラメータを取得
     *
     * @param userId ユーザーID
     * @return ラップされた累計パラメータDto
     */
    @GetMapping("/achievement/total")
    public Response<TotalParamDto> getTotalParam(@RequestParam("userId") String userId) {
        try {
            return ResponseCreator.succeed(achievementService.getTotalParam(userId));
        } catch (Exception e) {
            return ResponseCreator.fail(
                    ErrorCode.USER_DOES_NOT_EXIST, new UserValidationException(USER_DOES_NOT_EXIST,
                            "get total parameter", String.format("this user does not exist (userId: %d )", userId)),
                    null);
        }
    }
}
