package bobi.blog.services;

import bobi.blog.bindingModels.ArticleBindingModel;
import bobi.blog.entities.Article;
import bobi.blog.entities.User;
import javassist.NotFoundException;

public interface ArticleService {

    void create(ArticleBindingModel articleBindingModel, User author) throws NotFoundException;

    void update(Article article, ArticleBindingModel articleBindingModel) throws NotFoundException;

    void delete(Article article);

    Article getArticleById(Integer id) throws NotFoundException;
}
