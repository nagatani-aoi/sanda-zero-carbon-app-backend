package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.application.dto.TotalConditionDto;
import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.TotalConditionValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Achievement;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Mission;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.TotalCondition;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.AchievementRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.MissionRepository;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.TotalConditionRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;

/**
 * 市の状況サービス
 * 
 * @author ing
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

    /* -------------------- Create -------------------- */

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
            Mission mission = missionRepository.findById(achievement.getMissionId())
                    .orElseThrow(() -> new TotalConditionValidationException(
                            TOTAL_CONDITION_COULD_NOT_BE_CALUCULATE, "caliculate total condition",
                            String.format("Accessed a non-existent mission"))); // 達成したミッションを取得

            int hour = achievement.getHour();

            co2Reduction += mission.getCo2Reduction() * hour; // 削減CO2を加算
            costReduction += mission.getCostReduction() * hour; // 節約金額を加算
        }

        totalConditionRepository
                .save(new TotalCondition(null, costReduction, co2Reduction, since, until,
                        LocalDateTime.now())); // データベースに直近1時間の市の状況を登録
    }

    /* -------------------- Read -------------------- */

    /**
     * 全ての市の状況を集計して取得
     *
     * @return 市の状況DTO
     */
    public TotalConditionDto getAllTotalCondition() {
        double co2Reduction = 0d; // 削減CO2量
        double costReduction = 0d; // 節約金額

        List<TotalCondition> totalConditions = totalConditionRepository.findAll(); // 市の状況を全て取得

        for (TotalCondition totalCondition : totalConditions) { // リスト内の市の状況ごとに
            co2Reduction += totalCondition.getCo2Reduction(); // 削減CO2量を加算
            costReduction += totalCondition.getCostReduction(); // 節約金額を加算
        }

        return TotalConditionDto.build(co2Reduction, costReduction); // 市の状況DTOを生成して返す
    }

    /**
     * 直近1週間の市の状況を集計して取得
     *
     * @return 市の状況DTO
     */
    public TotalConditionDto getWeeklyTotalCondition() {
        double co2Reduction = 0d; // 削減CO2量
        double costReduction = 0d; // 節約金額

        LocalDateTime now = LocalDateTime.now(); // 現在日時を取得

        LocalDateTime since = now.with(DayOfWeek.MONDAY).with(LocalTime.of(0, 0, 0, 0)); // 現在週の月曜日を取得
        LocalDateTime until = now.with(DayOfWeek.SUNDAY).with(LocalTime.of(0, 0, 0, 0)); // 現在週の日曜日を取得

        List<TotalCondition> totalConditions = totalConditionRepository.findBySinceBetween(since, until); // 直近1週間の市の状況を取得

        for (TotalCondition totalCondition : totalConditions) { // リスト内の市の状況ごとに
            co2Reduction += totalCondition.getCo2Reduction(); // 削減CO2量を加算
            costReduction += totalCondition.getCostReduction(); // 節約金額を加算
        }

        return TotalConditionDto.build(co2Reduction, costReduction); // 市の状況DTOを生成して返す
    }
}
