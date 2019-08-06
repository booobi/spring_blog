package bobi.blog.Controller;

import bobi.blog.entities.Category;
import bobi.blog.repository.ArticleRepository;
import bobi.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String list(Model model) {

        List<Category> categories = this.categoryRepository.findAll();
        categories.stream().sorted(Comparator.comparingInt(Category::getId)).collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("view", "admin/category/list");

        return "base-layout";
    }
}
