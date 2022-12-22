package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;
import jp.kobespiral.sandazerocarbonappbackend.domain.utils.Rule;
import lombok.Data;

/**
 * @author sato
 */
@Data
public class UserDto {
    String userId; // ユーザID
    int age; // 年齢
    int totalPoint;// 累積ポイント
    int level;// レベル
    double nextLevelPercentage;// 次のレベルまでの割合
    int levelRate;// 1レベルあげるために必要なポイント
    Boolean levelFlag;//レベルアップフラグ
    Boolean mapFlag;//マップ解放フラグ

    public static UserDto build(User user) {
        UserDto dto = new UserDto();
        dto.userId = user.getUserId();
        dto.age = user.getAge();
        dto.totalPoint = user.getTotalPoint();
        dto.level = user.getTotalPoint() / Rule.levelRate + 1;
        dto.nextLevelPercentage = (double) (user.getTotalPoint() % Rule.levelRate) / Rule.levelRate;
        // System.out.println(dto.nextLevelPercentage);
        dto.levelRate = Rule.levelRate;
        dto.levelFlag = user.getLevelFlag();
        dto.mapFlag = user.getMapFlag();
        return dto;
    }
}
