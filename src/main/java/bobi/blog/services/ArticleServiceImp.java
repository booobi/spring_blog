package bobi.blog.services;

import bobi.blog.bindingModels.ArticleBindingModel;
import bobi.blog.bindingModels.ArticleCommentBindingModel;
import bobi.blog.entities.Article;
import bobi.blog.entities.Category;
import bobi.blog.entities.Tag;
import bobi.blog.entities.User;
import bobi.blog.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ArticleServiceImp implements ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImp(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void addArticle(ArticleBindingModel articleBindingModel, UserService userService, CategoryService categoryService, TagService tagService) {
        User user = userService.getCurrentUser();
        Category category = categoryService.getCategoryById(articleBindingModel.getCategoryId());
        Set<Tag> tags = tagService.findTagsFromString(articleBindingModel.getTagString());
        Article article = new Article(articleBindingModel.getTitle(), articleBindingModel.getContent(), user, category, tags);
        this.articleRepository.saveAndFlush(article);
    }

    @Override
    public void editArticle(Article article, ArticleBindingModel articleBindingModel, CategoryService categoryService, TagService tagService) {
        Category category = categoryService.getCategoryById(articleBindingModel.getCategoryId());
        Set<Tag> tags = tagService.findTagsFromString(articleBindingModel.getTagString());

        article.setTitle(articleBindingModel.getTitle());
        article.setContent(articleBindingModel.getContent());
        article.setCategory(category);
        article.setTags(tags);
    }

    @Override
    public void deleteArticle(Article article) {
        //TODO: implement deletion of comments, tags
        this.articleRepository.delete(article);
    }

    @Override
    public Article getArticleById(Integer id) {
        return this.articleRepository.getOne(id);
    }
}
