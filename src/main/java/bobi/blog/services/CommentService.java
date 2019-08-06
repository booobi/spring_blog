package bobi.blog.services;

import bobi.blog.bindingModels.ArticleCommentBindingModel;
import bobi.blog.entities.Article;
import bobi.blog.entities.Comment;

import java.util.Set;

public interface CommentService {
    Set<Comment> getAllByArticleOrderByIdDesc(Article article);
    void addComment(Article article, ArticleCommentBindingModel articleCommentBindingModel, UserService userService);
}
