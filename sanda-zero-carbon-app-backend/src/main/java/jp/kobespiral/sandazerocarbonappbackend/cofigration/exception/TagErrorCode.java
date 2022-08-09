package jp.kobespiral.sandazerocarbonappbackend.cofigration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * ユーザの共通エラーコード
 * @author sato
 */
@AllArgsConstructor // すべてのフィールドを引数に持つコンストラクタを生成 lombok.AllArgsConstructor
@Getter // 全フィールドのgetterを生成 lombok.Getter
public enum TagErrorCode {
    TAG_DOES_NOT_EXIST(11),
    TAG_ALREADY_EXISTS(12),
    OTHER_ERROR(99);
    private final int code;
}

