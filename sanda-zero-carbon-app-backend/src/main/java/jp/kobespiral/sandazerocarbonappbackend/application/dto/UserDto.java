package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;
import jp.kobespiral.sandazerocarbonappbackend.domain.utils.Rule;
import lombok.Data;
/**
 * @author sato
 */
@Data
public class UserDto {
    Long userId; // ユーザID
    String nickname; // ニックネーム
    int age; // 年齢
    int totalPoint;//累積ポイント
    int level;//レベル
    double nextLevelPercentage;//次のレベルまでの割合

    public static UserDto build(User user){
        UserDto dto = new UserDto();
        dto.userId = user.getUserId();
        dto.nickname = user.getNickname();
        dto.age = user.getAge();
        dto.totalPoint = user.getTotalPoint();
        dto.level = user.getTotalPoint()/Rule.levelRate + 1;
        dto.nextLevelPercentage = (user.getTotalPoint()%Rule.levelRate)/Rule.levelRate;
        return dto;
    }
}
