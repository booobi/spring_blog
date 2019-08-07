package bobi.blog.controllers;

import bobi.blog.bindingModels.ArticleBindingModel;
import bobi.blog.bindingModels.ArticleCommentBindingModel;
import bobi.blog.entities.*;
import bobi.blog.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final CommentService commentService;

    @Autowired
    public ArticleController(ArticleService articleService,
                             UserService userService,
                             CategoryService categoryService,
                             TagService tagService,
                             CommentService commentService) {
        this.articleService = articleService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    //TODO: move this to user service
    private boolean isUserAuthorOrAdmin(Article article) {
        User user = this.userService.getCurrentUser();

        return user.isAdmin() || user.isAuthor(article);
    }

    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        List<Category> categories = this.categoryService.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("view", "article/create");

        return "base-layout";
    }

    @PostMapping("article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(ArticleBindingModel articleBindingModel) {
        User author = this.userService.getCurrentUser();
        this.articleService.addArticle(articleBindingModel, author);

        return "redirect:/";
    }

    @GetMapping("article/{id}")
    public String details(Model model, @PathVariable Integer id) {

        Article article = this.articleService.getArticleById(id);

        if (article == null) {
            return "redirect:/";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            User entityUser = this.userService.getCurrentUser();
            //TODO: map to user view model
            model.addAttribute("user", entityUser);
        }

        Set<Comment> comments = this.commentService.getAllByArticleOrderByIdDesc(article);

        model.addAttribute("comments", comments);
        model.addAttribute("article", article);
        model.addAttribute("view", "article/details");

        return "base-layout";
    }

    @PostMapping("article/{id}")
    public String detailsCommentProcess(@PathVariable Integer id, ArticleCommentBindingModel articleCommentBindingModel) {
        Article article = this.articleService.getArticleById(id);

        if (article == null) {
            return "redirect:/";
        }

        this.commentService.addComment(article, articleCommentBindingModel, this.userService);

        return "redirect:/article/" + id;
    }

    @GetMapping("article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(Model model, @PathVariable Integer id) {
        Article article = this.articleService.getArticleById(id);

        if (article == null) {
            return "redirect:/";
        }

        if (!isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;
        }

        List<Category> categories = this.categoryService.getAllCategories();

        String tagString = article.getTags().stream().map(Tag::getName).collect(Collectors.joining(", "));

        model.addAttribute("tags", tagString);
        model.addAttribute("categories", categories);
        model.addAttribute("article", article);
        model.addAttribute("view", "article/edit");

        return "base-layout";
    }

    @PostMapping("article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, ArticleBindingModel articleBindingModel) {
        Article article = this.articleService.getArticleById(id);

        if (article == null) {
            return "redirect:/";
        }

        if (!isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;
        }

        this.articleService.editArticle(article, articleBindingModel, categoryService, tagService);

        return "redirect:/article/" + article.getId();
    }

    @GetMapping("article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id) {
        Article article = this.articleService.getArticleById(id);

        if (article == null) {
            return "redirect:/";
        }

        if (!isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;
        }

        model.addAttribute("article", article);
        model.addAttribute("view", "article/delete");

        return "base-layout";
    }

    @PostMapping("article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id) {
        Article article = this.articleService.getArticleById(id);

        if (article == null) {
            return "redirect:/";
        }

        if (!isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;
        }

        return "redirect:/";
    }
}
