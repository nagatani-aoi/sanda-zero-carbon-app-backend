package jp.kobespiral.sandazerocarbonappbackend.cofigration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * ユーザの共通エラーコード
 * @author sato
 */
@AllArgsConstructor // すべてのフィールドを引数に持つコンストラクタを生成 lombok.AllArgsConstructor
@Getter // 全フィールドのgetterを生成 lombok.Getter
public enum UserErrorCode {
    USER_DOES_NOT_EXIST(11), USER_ALREADY_EXISTS(12), UID_CAN_NOT_BE_CHANGED(13), USER_NICKNAME_CAN_NOT_BE_SET_NULL(14),
    USER_EMAIL_CAN_NOT_BE_SET_NULL(15), WORK_DOES_NOT_EXIST(21), WORK_ALREADY_EXISTS(22),
    WORK_WNAME_CAN_NOT_BE_SET_NULL(23), WID_CAN_NOT_BE_CHANGED(24), WORKED_WORK_CAN_NOT_BE_UPDETED(25),
    WORKED_WORK_CAN_NOT_BE_DELETED(26), WORKSTATE_DOES_NOT_EXIST(31), WORKSTATE_ALREADY_EXISTS(32),
    EVENT_START_END_NOT_MATCH(41), EVENT_START_END_NOT_MATCH_WID(42), WORKEVENT_DOES_NOT_EXIST(43), EVENT_NOT_FAST(51),
    WORKLOG_DOES_NOT_EXIST(52), WORKLOG_CAN_NOT_CREATE_WORKING(53), UID_DOSE_NOT_MATCH(54),
    STARTEVENT_NOT_OLDER_ENDEVENT(55), UNTIL_AND_SINCE_CAN_NOT_SAME(56), PERSE_EXCEPTION(61),
    COMMENT_DOES_NOT_EXIST(71), LOGINLOG_ALREADY_EXISTS(81), LOGINLOG_DOES_NOT_EXIST(82),
    WORKTIMECHALLENGE_CAN_NOT_CREATE_WORKING(91), UNTIL_CAN_NOT_SET_IN_THE_PAST(92),
    WORKTIMECHALLENGE_DOES_NOT_EXIST(93), CONTROLLER_VALIDATION_ERROR(97), CONTROLLER_REJECTED(98), OTHER_ERROR(99);
    private final int code;
}

