package bobi.blog.controllers;

import bobi.blog.config.Consts;
import bobi.blog.entities.Article;
import bobi.blog.entities.Category;
import bobi.blog.services.CategoryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Controller
public class HomeController {
    private CategoryService categoryService;

    @Autowired
    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Category> categories = this.categoryService.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("view", "home/index");

        return Consts.BASE_LAYOUT;
    }

    @GetMapping("/category/{id}")
    public String listArticles(@PathVariable Integer id, Model model) throws NotFoundException {
        Category category = this.categoryService.getCategoryById(id);

        Set<Article> articles = category.getArticles();

        model.addAttribute("articles", articles);
        model.addAttribute("category", category);
        model.addAttribute("view", "home/list-articles");

        return Consts.BASE_LAYOUT;
    }

    @GetMapping("/error/403")
    public String accessDenied(Model model) {

        model.addAttribute("view", "error/403");

        return Consts.BASE_LAYOUT;
    }
}
