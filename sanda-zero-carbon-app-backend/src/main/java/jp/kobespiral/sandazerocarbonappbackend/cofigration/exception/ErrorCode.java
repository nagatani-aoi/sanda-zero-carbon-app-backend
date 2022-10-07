package jp.kobespiral.sandazerocarbonappbackend.cofigration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * エラーコード
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_DOES_NOT_EXIST(11),
    USER_ALREADY_EXISTS(12),
    ADMIN_DOES_NOT_EXISTS(13),
    ADMIN_ALREADY_EXISTS(14),
    TAG_DOES_NOT_EXIST(15),
    TAG_ALREADY_EXISTS(16),
    MISSION_DOES_NOT_EXIST(17),
    NO_TAG_CORRESPONDING_TO_THE_MISSION(18),
    CONTROLLER_VALIDATION_ERROR(97),
    CONTROLLER_REJECTED(98),
    OTHER_ERROR(99);

    private final int code;
}
