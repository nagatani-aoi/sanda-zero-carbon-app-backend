package jp.kobespiral.sandazerocarbonappbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.User;

/**
 * ユーザ リポジトリ
 * 
 * @author Kamae
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
}
