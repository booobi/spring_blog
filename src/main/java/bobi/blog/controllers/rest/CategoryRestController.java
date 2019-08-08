package bobi.blog.controllers.rest;

import bobi.blog.config.Consts;
import bobi.blog.entities.Article;
import bobi.blog.entities.Category;
import bobi.blog.models.viewModels.ArticleViewModel;
import bobi.blog.models.viewModels.CategoryViewModel;
import bobi.blog.services.ArticleService;
import bobi.blog.services.CategoryService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Consts.REST_SERVICES_URL)
public class CategoryRestController {
    private final CategoryService categoryService;
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryRestController(CategoryService categoryService, ArticleService articleService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/category/all")
    public List<CategoryViewModel> listAllCategories() {
        List<Category> allCategories = this.categoryService.getAllCategories();

        return allCategories.stream().map(category -> this.modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());
    }

    @GetMapping("/category/{id}/all")
    public List<ArticleViewModel> listArticlesByCategory(@PathVariable("id") Integer id) throws NotFoundException {
        Category category = this.categoryService.getCategoryById(id);
        List<Article> categoryArticles = this.articleService.getArticlesByCategory(category);

        return categoryArticles.stream().map(article -> modelMapper.map(article, ArticleViewModel.class)).collect(Collectors.toList());
    }
}
