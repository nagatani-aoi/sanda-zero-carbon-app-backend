package jp.kobespiral.sandazerocarbonappbackend.domain.utils;

/**
 * ルールのstaticクラス
 *
 * @author ing
 */
public class Rule {
    /** 1レベルあがるのに必要なポイント */
    public static int levelRate = 50;
    /** この数の倍数でマップが解放されていく */
    public static int mapLevelRate = 5;
    /** 1日にミッションで得られるポイント */
    public static int maxMissionPoint = 70;
    /** 1日にクイズで得られるポイント */
    public static int maxQuizPoint = 30;
}
