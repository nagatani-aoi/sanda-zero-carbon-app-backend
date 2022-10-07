package jp.kobespiral.sandazerocarbonappbackend.cofigration.exception;

public class TagValidationException extends RuntimeException{
    /** シリアライズであることを保証？ */
    private final static long serialVersionUID = 1L;
    /** エラーの種類識別子 */
    private final ErrorCode code;
    /**
     * 例外を生成するコンストラクタ
     *
     * @param code  エラーコード
     * @param error 発生したエラーの内容を説明する文字列
     * @param cause 発生したエラーの原因を説明する文字列
     */
    public TagValidationException(ErrorCode code, String error, String cause) {
        super(String.format("fail to %s, because %s.", error, cause));
        this.code = code;
    }
}
