package ro.iss.agorainretea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.iss.agorainretea.domain.Article;
import ro.iss.agorainretea.domain.User;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findAllByIsPublished(Boolean isPublished);

    List<Article> findAllByAuthor_Id(long authorId);

    List<Article> findAllByAuthor_IdAndIsPublished(long authorId, Boolean isPublished);
}
