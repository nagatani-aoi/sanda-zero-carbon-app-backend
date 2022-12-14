package jp.kobespiral.sandazerocarbonappbackend.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.domain.entity.User;

/**
 * ユーザ リポジトリ
 * 
 * @author Kamae
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByUserIdAndPassword(String userId, String password);
    Boolean existsByUserIdAndPassword(String userId, String password);
}
