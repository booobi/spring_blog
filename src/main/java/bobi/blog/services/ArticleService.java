package bobi.blog.services;

import bobi.blog.bindingModels.ArticleBindingModel;
import bobi.blog.entities.Article;
import bobi.blog.entities.User;

public interface ArticleService {

    //CRUD
    void addArticle(ArticleBindingModel articleBindingModel, User author);
    void editArticle(Article article, ArticleBindingModel articleBindingModel, CategoryService categoryService, TagService tagService);
    void deleteArticle(Article article);

    Article getArticleById(Integer id);
}
