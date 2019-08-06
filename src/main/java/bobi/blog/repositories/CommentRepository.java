package bobi.blog.repositories;

import bobi.blog.entities.Article;
import bobi.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public Set<Comment> findAllByArticleOrderByIdDesc(Article article);
}
