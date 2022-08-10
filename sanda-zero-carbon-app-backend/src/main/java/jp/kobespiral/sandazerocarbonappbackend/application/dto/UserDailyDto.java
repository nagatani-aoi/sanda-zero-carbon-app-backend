package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.UserDailyStatus;
import jp.kobespiral.sandazerocarbonappbackend.domain.utils.Rule;
import lombok.Data;

/**
 * @author sato
 */
@Data
public class UserDailyDto {
    int dailyTotalPoint; //今日の累積ポイント
    int dailyMaxMissionPoint; //一日の獲得ポイント上限

    public static UserDailyDto build(UserDailyStatus userDailyStatus){
        UserDailyDto dto = new UserDailyDto();
        dto.dailyTotalPoint = userDailyStatus.getTotalPoint();
        dto.dailyMaxMissionPoint = Rule.maxMissionPoint;
        return dto;
    }
}
