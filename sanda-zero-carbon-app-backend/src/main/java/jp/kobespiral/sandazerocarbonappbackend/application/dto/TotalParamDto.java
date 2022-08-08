package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import lombok.Data;

/**
 * ユーザーの累計パラメータのDto
 *
 * @author ing
 */
@Data
public class TotalParamDto {
    /** ユーザーID */
    Long userId;
    /** 累計CO2削減量 */
    double totalCO2Reduction;
    /** 累計節約金額 */
    double totalCostReduction;
    /** ミッションクリア数 */
    int achievementNum;
}
