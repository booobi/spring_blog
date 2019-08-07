package bobi.blog.services;

import bobi.blog.bindingModels.ArticleBindingModel;
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
    private final ArticleRepository articleRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Autowired
    public ArticleServiceImp(ArticleRepository articleRepository,
                             CategoryService categoryService,
                             TagService tagService) {
        this.articleRepository = articleRepository;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @Override
    public void addArticle(ArticleBindingModel articleBindingModel, User author) {
        Category category = this.categoryService.getCategoryById(articleBindingModel.getCategoryId());
        Set<Tag> tags = this.tagService.findTagsFromString(articleBindingModel.getTagString());
        Article article = new Article(articleBindingModel.getTitle(), articleBindingModel.getContent(), author, category, tags);
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

        this.articleRepository.saveAndFlush(article);
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
