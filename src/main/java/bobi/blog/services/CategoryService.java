package bobi.blog.services;

import bobi.blog.bindingModels.CategoryBindingModel;
import bobi.blog.entities.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
    void addCategory(CategoryBindingModel categoryBindingModel);
    void editCategory(Category category, CategoryBindingModel categoryBindingModel);
    void deleteCategory(Category category);
}
