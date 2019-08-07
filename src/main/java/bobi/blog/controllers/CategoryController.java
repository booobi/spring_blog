package bobi.blog.controllers;

import bobi.blog.models.bindingModels.CategoryBindingModel;
import bobi.blog.config.Consts;
import bobi.blog.entities.Article;
import bobi.blog.entities.Category;
import bobi.blog.services.ArticleService;
import bobi.blog.services.CategoryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    private CategoryService categoryService;
    private ArticleService articleService;

    @Autowired
    public CategoryController(CategoryService categoryService,
                              ArticleService articleService) {
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String list(Model model) {

        List<Category> categories = this.categoryService.getAllCategories();
        categories.stream().sorted(Comparator.comparingInt(Category::getId)).collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("view", "admin/category/list");

        return Consts.BASE_LAYOUT;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("view", "admin/category/create");

        return Consts.BASE_LAYOUT;
    }

    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel) {
        if (StringUtils.isEmpty(categoryBindingModel.getName())) {
            return "redirect:/admin/categories/create";
        }

        this.categoryService.create(categoryBindingModel);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) throws NotFoundException {
        Category category = this.categoryService.getCategoryById(id);

        if (category == null) {
            return "redirect:/admin/categories/";
        }

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/edit");

        return Consts.BASE_LAYOUT;
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel) throws NotFoundException {
        Category category = this.categoryService.getCategoryById(id);

        if (category == null) {
            return "redirect:/admin/categories/";
        }
        this.categoryService.update(category, categoryBindingModel);

        return "redirect:/admin/categories/";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) throws NotFoundException {

        Category category = this.categoryService.getCategoryById(id);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/delete");

        return Consts.BASE_LAYOUT;
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) throws NotFoundException {

        Category category = this.categoryService.getCategoryById(id);

        for (Article article : category.getArticles()) {
            this.articleService.delete(article);
        }
        this.categoryService.delete(category);

        return "redirect:/admin/categories/";
    }
}
