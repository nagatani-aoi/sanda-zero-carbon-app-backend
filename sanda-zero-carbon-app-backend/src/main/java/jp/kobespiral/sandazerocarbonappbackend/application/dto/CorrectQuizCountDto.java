package jp.kobespiral.sandazerocarbonappbackend.application.dto;

import lombok.Data;

@Data
public class CorrectQuizCountDto {
    String userId;
    int totalQuizSize;
    int correctQuizCount;

    public static CorrectQuizCountDto build(String userId, int totalQuizSize, int correctQuizCount) {
        CorrectQuizCountDto dto = new CorrectQuizCountDto();

        dto.userId = userId;
        dto.totalQuizSize = totalQuizSize;
        dto.correctQuizCount = correctQuizCount;

        return dto;
    }
}