package bobi.blog.services;

import bobi.blog.entities.Category;
import bobi.blog.models.bindingModels.CategoryBindingModel;
import javassist.NotFoundException;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Integer id) throws NotFoundException;

    void create(CategoryBindingModel categoryBindingModel);

    void update(Category category, CategoryBindingModel categoryBindingModel);

    void delete(Category category);
}
