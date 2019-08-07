package bobi.blog.services;

import bobi.blog.models.bindingModels.ArticleCommentBindingModel;
import bobi.blog.entities.Article;
import bobi.blog.entities.Comment;
import bobi.blog.entities.User;

import java.util.Set;

public interface CommentService {
    Set<Comment> getAllByArticleOrderByIdDesc(Article article);

    void addComment(Article article, ArticleCommentBindingModel articleCommentBindingModel, User author);

    void delete(Comment comment);
}
