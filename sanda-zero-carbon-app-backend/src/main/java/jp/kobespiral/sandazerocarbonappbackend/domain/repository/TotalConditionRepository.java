package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.TotalCondition;

/**
 * 市の状況のリポジトリ
 * 
 * @author sato
 */
@Repository
public interface TotalConditionRepository extends CrudRepository<TotalCondition, Long> {
    /**
     * @return 全てのトータルコンディション
     */
    List<TotalCondition> findAll();

    /**
     * 市全体のCO2削減状況を期間で検索する
     * 
     * @param since フィルター期間の始まり
     * @param until フィルター期間の終わり
     * @return 指定された期間でフィルター済みのトータルコンディションのリスト
     */
    List<TotalCondition> findByRecordedAtBetween(LocalDateTime since, LocalDateTime until);
}
