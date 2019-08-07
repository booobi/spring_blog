package bobi.blog.repositories;

import bobi.blog.entities.Article;
import bobi.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> getAllByCategory(Category category);
}
