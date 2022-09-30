package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import lombok.Data;

/**
 * 市の状況のDTO
 *
 * @author ing
 */
@Data
public class TotalConditionDto {
    /** 削減CO2量 */
    double co2Reduction;
    /** 節約金額 */
    double costReduction;

    /**
     * 削減CO2量と節約金額から市の状況DTOを作成
     *
     * @param co2Reduction  削減CO2量
     * @param costReduction 節約金額
     * @return 市の状況DTO
     */
    public static TotalConditionDto build(double co2Reduction, double costReduction) {
        // 市の状況DTOに各変数をセット
        TotalConditionDto dto = new TotalConditionDto();

        dto.co2Reduction = co2Reduction;
        dto.costReduction = costReduction;

        return dto;
    }
}
