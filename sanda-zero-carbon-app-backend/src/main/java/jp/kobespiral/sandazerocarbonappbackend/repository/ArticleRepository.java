package jp.kobespiral.sandazerocarbonappbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.sandazerocarbonappbackend.entity.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article,Long>{
    List<Article> findByTagId(Long tagId);
    List<Article> findByIsImportantTrue();
    List<Article> findByTitleContaining(String title);
}