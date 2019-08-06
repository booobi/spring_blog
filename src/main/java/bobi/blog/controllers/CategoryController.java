package bobi.blog.controllers;

import bobi.blog.bindingModels.CategoryBindingModel;
import bobi.blog.entities.Article;
import bobi.blog.entities.Category;
import bobi.blog.repositories.ArticleRepository;
import bobi.blog.repositories.CategoryRepository;
import bobi.blog.services.CategoryService;
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
    private ArticleRepository articleRepository;

    @Autowired
    public CategoryController(CategoryService categoryService,
                              ArticleRepository articleRepository) {
        this.categoryService = categoryService;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String list(Model model) {

        List<Category> categories = this.categoryService.getAllCategories();
        categories.stream().sorted(Comparator.comparingInt(Category::getId)).collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("view", "admin/category/list");

        return "base-layout";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("view", "admin/category/create");

        return "base-layout";
    }

    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel) {
        if (StringUtils.isEmpty(categoryBindingModel.getName())) {
            return "redirect:/admin/categories/create";
        }

        this.categoryService.addCategory(categoryBindingModel);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Category category = this.categoryService.getCategoryById(id);

        if(category == null) {
            return "redirect:/admin/categories/";
        }

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel) {
        Category category = this.categoryService.getCategoryById(id);

        if(category == null) {
            return "redirect:/admin/categories/";
        }
        this.categoryService.editCategory(category, categoryBindingModel);

        return "redirect:/admin/categories/";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {

        Category category = this.categoryService.getCategoryById(id);

        if(category == null){
            return "redirect:/admin/categories/";
        }

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {

        Category category = this.categoryService.getCategoryById(id);

        if(category == null){
            return "redirect:/admin/categories/";
        }

        for(Article article : category.getArticles()) {
            this.articleRepository.delete(article);
        }
        this.categoryService.deleteCategory(category);

        return "redirect:/admin/categories/";
    }
}
