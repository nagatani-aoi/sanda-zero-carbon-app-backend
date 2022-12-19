package jp.kobespiral.sandazerocarbonappbackend.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.MapDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.Response;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ResponseCreator;
import jp.kobespiral.sandazerocarbonappbackend.domain.service.MapService;
import lombok.RequiredArgsConstructor;

/**
 * マップのRESTController
 *
 * @author ing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MapRestController {
    /** マップのサービス */
    private final MapService mapService;

    /* -------------------- Read -------------------- */

    /**
     * 指定したユーザーの現在地からマップを取得
     *
     * @param userId          ユーザーID
     * @param currentLocation 現在地
     * @return ラップされたマップDto
     */
    @GetMapping("/map")
    public Response<MapDto> getMap(@RequestParam("userId") String userId,
            @RequestParam("currentLocation") int currentLocation) {
        return ResponseCreator.succeed(mapService.getMap(userId, currentLocation));
        // try {
        //     return ResponseCreator.succeed(mapService.getMap(userId, currentLocation));
        // } catch (Exception e) {
        //     System.err.println(e.toString());
        //     return ResponseCreator.fail(ErrorCode.USER_DOES_NOT_EXIST, e, null);
        // }
    }

    /**
     * 指定したユーザーの初期位置のマップを取得
     *
     * @param userId ユーザーID
     * @return ラップされたマップDto
     */
    @GetMapping("/map/initialLocation")
    public Response<MapDto> getMapOnInitialLocation(@RequestParam("userId") String userId) {
        return ResponseCreator.succeed(mapService.getMapOnInitialLocation(userId));
        // try {
        //     return ResponseCreator.succeed(mapService.getMapOnInitialLocation(userId));
        // } catch (Exception e) {
        //     return ResponseCreator.fail(ErrorCode.USER_DOES_NOT_EXIST, new UserValidationException(USER_DOES_NOT_EXIST,
        //             "get map on initial location", String.format("this user does not exist (userId: %d )", userId)),
        //             null);
        // }
    }
}
