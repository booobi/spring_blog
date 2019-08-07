package bobi.blog.services;

import bobi.blog.bindingModels.ArticleCommentBindingModel;
import bobi.blog.entities.Article;
import bobi.blog.entities.Comment;
import bobi.blog.entities.User;
import bobi.blog.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Set<Comment> getAllByArticleOrderByIdDesc(Article article) {
        return this.commentRepository.findAllByArticleOrderByIdDesc(article);
    }

    @Override
    public void addComment(Article article, ArticleCommentBindingModel articleCommentBindingModel, User author) {

        Comment comment = new Comment(article, articleCommentBindingModel.getContent(), author);
        this.commentRepository.saveAndFlush(comment);
    }

    @Override
    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }
}
