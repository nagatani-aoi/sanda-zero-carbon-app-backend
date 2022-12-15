package jp.kobespiral.sandazerocarbonappbackend.domain.entity;

/**
 * ミッションの難易度別ポイントの定義
 * 
 * @author Sato
 */
public enum MissionPoint {
    easy(5),
    normal(10),
    hard(15),
    ;

    private final int id;

    private MissionPoint(final int id) {
        this.id = id;
    }

    public int getInt() {
        return this.id;
    }
}