package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.UserDailyStatus;
import jp.kobespiral.sandazerocarbonappbackend.domain.utils.Rule;
import lombok.Data;

/**
 * @author sato
 */
@Data
public class UserDailyDto {
    int dailyMissionPoint; //今日のミッション累積ポイント
    int dailyQuizPoint; //今日のクイズ累積ポイント
    int dailyMaxMissionPoint; //一日の獲得ポイント上限

    public static UserDailyDto build(UserDailyStatus userDailyStatus){
        UserDailyDto dto = new UserDailyDto();
        dto.dailyMissionPoint = userDailyStatus.getTotalMissionPoint();
        dto.dailyQuizPoint = userDailyStatus.getTotalQuizPoint();
        dto.dailyMaxMissionPoint = Rule.maxMissionPoint;
        return dto;
    }
}
