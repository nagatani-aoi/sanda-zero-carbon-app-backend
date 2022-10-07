package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.TotalCondition;

/**
 * 市の状況のリポジトリ
 * 
 * @author sato & ing
 */
@Repository
public interface TotalConditionRepository extends CrudRepository<TotalCondition, Long> {
    /**
     * @return 全てのトータルコンディション
     */
    List<TotalCondition> findAll();

    /**
     * 直近の市の状況を取得
     *
     * @return 直近の市の状況
     */
    TotalCondition findFirstByOrderByRecordedAtDesc();

    // 参考
    // https://ti-tomo-knowledge.hatenablog.com/entry/2018/10/18/095121

    /**
     * 市全体のCO2削減状況を期間で検索する
     * 
     * @param since フィルター期間の始まり
     * @param until フィルター期間の終わり
     * @return 指定された期間でフィルター済みのトータルコンディションのリスト
     */
    List<TotalCondition> findBySinceBetween(LocalDateTime since, LocalDateTime until);
}
