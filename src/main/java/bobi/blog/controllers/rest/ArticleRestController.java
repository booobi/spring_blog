package bobi.blog.controllers.rest;

import bobi.blog.entities.Article;
import bobi.blog.entities.Comment;
import bobi.blog.models.viewModels.CommentViewModel;
import bobi.blog.services.ArticleService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/api")
public class ArticleRestController {
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleRestController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/article/{id}/comments/all")
    public List<CommentViewModel> listCommentsByArticle(@PathVariable("id") Integer id) throws NotFoundException {
        Article article = this.articleService.getArticleById(id);
        Set<Comment> comments = article.getComments();

        return comments.stream().map(comment -> modelMapper.map(comment, CommentViewModel.class)).collect(Collectors.toList());
    }
}
