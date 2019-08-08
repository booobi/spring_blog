package bobi.blog.services;

import bobi.blog.entities.Article;
import bobi.blog.entities.Category;
import bobi.blog.entities.User;
import bobi.blog.models.bindingModels.ArticleBindingModel;
import javassist.NotFoundException;

import java.util.List;

public interface ArticleService {

    void create(ArticleBindingModel articleBindingModel, User author) throws NotFoundException;

    void update(Article article, ArticleBindingModel articleBindingModel) throws NotFoundException;

    void delete(Article article);

    Article getArticleById(Integer id) throws NotFoundException;

    List<Article> getArticlesByCategory(Category category);
}
