package bobi.blog.services;

import bobi.blog.entities.*;
import bobi.blog.models.bindingModels.ArticleBindingModel;
import bobi.blog.repositories.ArticleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImp implements ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final CommentService commentService;

    @Autowired
    public ArticleServiceImp(ArticleRepository articleRepository, CategoryService categoryService, TagService tagService, CommentService commentService) {
        this.articleRepository = articleRepository;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.commentService = commentService;
    }


    @Override
    public void create(ArticleBindingModel articleBindingModel, User author) throws NotFoundException {
        Category category = this.categoryService.getCategoryById(articleBindingModel.getCategoryId());
        Set<Tag> tags = this.tagService.findTagsFromString(articleBindingModel.getTagString());
        Article article = new Article(articleBindingModel.getTitle(), articleBindingModel.getContent(), author, category, tags);
        this.articleRepository.saveAndFlush(article);
    }

    @Override
    public void update(Article article, ArticleBindingModel articleBindingModel) throws NotFoundException {
        Category category = this.categoryService.getCategoryById(articleBindingModel.getCategoryId());
        Set<Tag> tags = this.tagService.findTagsFromString(articleBindingModel.getTagString());

        article.setTitle(articleBindingModel.getTitle());
        article.setContent(articleBindingModel.getContent());
        article.setCategory(category);
        article.setTags(tags);

        this.articleRepository.saveAndFlush(article);
    }

    @Override
    public void delete(Article article) {
        //delete all comments
        Set<Comment> comments = article.getComments();
        for (Comment comment : comments) {
            this.commentService.delete(comment);
        }

        this.articleRepository.delete(article);
    }

    @Override
    public Article getArticleById(Integer id) throws NotFoundException {
        if (!this.articleRepository.exists(id)) {
            throw new NotFoundException("Article not found!");
        }

        return this.articleRepository.findOne(id);


    }

    @Override
    public List<Article> getArticlesByCategory(Category category) {

        return this.articleRepository.getAllByCategory(category);

    }
}
