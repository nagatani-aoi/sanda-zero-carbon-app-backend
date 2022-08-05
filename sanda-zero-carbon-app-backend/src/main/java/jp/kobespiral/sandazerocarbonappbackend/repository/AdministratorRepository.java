package jp.kobespiral.sandazerocarbonappbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.Administrator;

/**
 * アドミン リポジトリ
 * 
 * @author Kamae
 */
@Repository
public interface AdministratorRepository extends CrudRepository<Administrator, Long> {
    
}