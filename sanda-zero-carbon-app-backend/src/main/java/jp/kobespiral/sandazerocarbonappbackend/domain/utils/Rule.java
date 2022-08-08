package jp.kobespiral.sandazerocarbonappbackend.domain.utils;

/**
 * ルールのstaticクラス
 *
 * @author ing
 */
public class Rule {
    public static class innerRule {
        /** 1レベルあがるのに必要なポイント */
        public static int levelRate = 100;
        /** 1日にミッションで得られるポイント */
        public static int maxMissionPoint = 70;
        /** 1日にクイズで得られるポイント */
        public static int maxQuizPoint = 30;
    }
}