package jp.kobespiral.sandazerocarbonappbackend.cofigration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * エラーコード
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
    NO_SUCH_USER_EXISTS(11),
    USER_ALREADY_EXISTS(12),
    INVALID_USER_INFO(13),
    INVALID_USER_OPERATION(14),
    NO_SUCH_ADMIN_EXISTS(21),
    ADMIN_ALREADY_EXISTS(22),
    INVALID_ADMIN_INFO(23),
    INVALID_ADMIN_OPERATION(24),
    NO_SUCH_TAG_EXISTS(31),
    TAG_ALREADY_EXISTS(32),
    INVALID_TAG_INFO(33),
    INVALID_TAG_OPERATION(34),
    NO_SUCH_MISSION_EXISTS(41),
    MISSION_ALREADY_EXISTS(42),
    INVALID_MISSION_INFO(43),
    INVALID_MISSION_OPERATION(44),
    NO_SUCH_QUIZ_EXISTS(51),
    QUIZ_ALREADY_EXISTS(52),
    INVALID_QUIZ_INFO(53),
    INVALID_QUIZ_OPERATION(54),
    NO_SUCH_ANSWERED_QUIZ_EXISTS(55),
    ANSWERED_QUIZ_ALREADY_EXISTS(56),
    INVALID_ANSWERED_QUIZ_INFO(57),
    INVALID_ANSWERED_QUIZ_OPERATION(58),
    NO_SUCH_ARTICLE_EXISTS(61),
    ARTICLE_ALREADY_EXISTS(62),
    INVALID_ARTICLE_INFO(63),
    INVALID_ARTICLE_OPERATION(64),
    NO_TAG_CORRESPONDING_TO_THE_MISSION(71),
    NO_TAG_CORRESPONDING_TO_THE_QUIZ(72),
    TOTAL_CONDITION_COULD_NOT_BE_CALUCULATE(81),
    CONTROLLER_VALIDATION_ERROR(97),
    CONTROLLER_REJECTED(98),
    OTHER_ERROR(99);

    private final int code;
}
