package bobi.blog.services;

import bobi.blog.bindingModels.CategoryBindingModel;
import bobi.blog.entities.Category;
import javassist.NotFoundException;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Integer id) throws NotFoundException;

    void create(CategoryBindingModel categoryBindingModel);

    void update(Category category, CategoryBindingModel categoryBindingModel);

    void delete(Category category);
}
