package bobi.blog.services;

import bobi.blog.bindingModels.ArticleBindingModel;
import bobi.blog.entities.Article;

public interface ArticleService {
    public void addArticle(ArticleBindingModel articleBindingModel, UserService userService, CategoryService categoryService, TagService tagService);
    public void editArticle(Article article, ArticleBindingModel articleBindingModel, CategoryService categoryService, TagService tagService);
    public void deleteArticle(Article article);
    public Article getArticleById(Integer id);
}
