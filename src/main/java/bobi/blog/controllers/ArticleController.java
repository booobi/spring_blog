package bobi.blog.controllers;

import bobi.blog.models.bindingModels.ArticleBindingModel;
import bobi.blog.models.bindingModels.ArticleCommentBindingModel;
import bobi.blog.config.Consts;
import bobi.blog.entities.*;
import bobi.blog.services.*;
import javassist.NotFoundException;
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

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @Autowired
    public ArticleController(ArticleService articleService,
                             UserService userService,
                             CategoryService categoryService,
                             CommentService commentService) {
        this.articleService = articleService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.commentService = commentService;
    }

    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        List<Category> categories = this.categoryService.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("view", "article/create");

        return Consts.BASE_LAYOUT;
    }

    @PostMapping("article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(ArticleBindingModel articleBindingModel) throws NotFoundException {
        User author = this.userService.getCurrentUser();
        this.articleService.create(articleBindingModel, author);

        return "redirect:/";
    }

    @GetMapping("article/{id}")
    public String details(Model model, @PathVariable Integer id) throws NotFoundException {
        Article article = this.articleService.getArticleById(id);

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            User entityUser = this.userService.getCurrentUser();
            //TODO: map to user view model
            model.addAttribute("user", entityUser);
        }

        Set<Comment> comments = this.commentService.getAllByArticleOrderByIdDesc(article);

        model.addAttribute("comments", comments);
        model.addAttribute("article", article);
        model.addAttribute("view", "article/details");

        return Consts.BASE_LAYOUT;
    }

    @PostMapping("article/{id}")
    public String detailsCommentProcess(@PathVariable Integer id, ArticleCommentBindingModel articleCommentBindingModel) throws NotFoundException {
        Article article = this.articleService.getArticleById(id);

        User author = this.userService.getCurrentUser();
        this.commentService.addComment(article, articleCommentBindingModel, author);

        return "redirect:/article/" + id;
    }

    @GetMapping("article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(Model model, @PathVariable Integer id) throws NotFoundException {
        Article article = this.articleService.getArticleById(id);

        if (!this.userService.isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;
        }

        List<Category> categories = this.categoryService.getAllCategories();
        String tagString = article.getTagString();

        model.addAttribute("tags", tagString);
        model.addAttribute("categories", categories);
        model.addAttribute("article", article);
        model.addAttribute("view", "article/edit");

        return Consts.BASE_LAYOUT;
    }

    @PostMapping("article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, ArticleBindingModel articleBindingModel) throws NotFoundException {
        Article article = this.articleService.getArticleById(id);

        if (!this.userService.isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;
        }

        this.articleService.update(article, articleBindingModel);

        return "redirect:/article/" + article.getId();
    }

    @GetMapping("article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id) throws NotFoundException {
        Article article = this.articleService.getArticleById(id);

        if (!this.userService.isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;
        }

        model.addAttribute("article", article);
        model.addAttribute("view", "article/delete");

        return Consts.BASE_LAYOUT;
    }

    @PostMapping("article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id) throws NotFoundException {
        Article article = this.articleService.getArticleById(id);

        if (!this.userService.isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;
        }

        this.articleService.delete(article);

        return "redirect:/";
    }
}
