package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Achievement;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.TotalCondition;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.AchievementRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TotalConditionRepository;
import lombok.RequiredArgsConstructor;

/**
 * 市の状況サービス
 */
@RequiredArgsConstructor
@Service
public class TotalConditionService {
    /** 市の状況リポジトリ */
    private final TotalConditionRepository totalConditionRepository;
    /** 達成のリポジトリ */
    private final AchievementRepository achievementRepository;
    /** ミッションのリポジトリ */
    private final MissionRepository missionRepository;

    /**
     * 直近1時間の市の状況を計算し，DBに保存するメソッド
     *
     * @return
     */
    public void calculateTotalCondition() {
        int INTERVAL = 1;

        TotalCondition totalCondition = totalConditionRepository.findFirstByOrderByRecordedAtDesc(); // 直近の市の状況を取得

        LocalDateTime since; // 市の状況を計算する時間指定がいつからか
        LocalDateTime until; // 市の状況を計算する時間指定がいつまでか

        if (totalCondition == null) { // 市の状況が存在しない場合
            until = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0); // 現在時刻の分以下切り捨てを指定

            // 参考
            // https://qiita.com/swordone/items/5fbf5ed138d812e117ec

            since = until.minusHours(INTERVAL); // 生成する市の状況のいつからをいつまでの1時間前に指定
        } else { // 市の状況が存在する場合
            since = totalCondition.getUntil(); // 直近の市の状況の日時の範囲のいつまでを次の市の状況のいつからに指定

            until = since.plusHours(INTERVAL); // 生成する市の状況のいつまでをいつからの1時間後に指定

            // 参考
            // https://docs.oracle.com/javase/jp/8/docs/api/java/time/LocalDateTime.html
        }

        List<Achievement> achievements = achievementRepository.findByAchievedAtBetween(since, until); // 日時の範囲指定した達成のリストを取得

        double co2Reduction = 0d; // 合計削減CO2
        double costReduction = 0d; // 合計節約金額

        for (Achievement achievement : achievements) { // 達成のリストに関してそれぞれ
            Mission mission = missionRepository.findById(achievement.getMissionId()).orElseThrow(); // 達成したミッションを取得

            int hour = achievement.getHour();

            co2Reduction += mission.getCo2Reduction() * hour; // 削減CO2を加算
            costReduction += mission.getCostReduction() * hour; // 節約金額を加算
        }

        totalConditionRepository
                .save(new TotalCondition(null, costReduction, co2Reduction, since, until,
                        LocalDateTime.now())); // データベースに直近1時間の市の状況を登録
    }
}
