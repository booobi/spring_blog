package bobi.blog.services;

import bobi.blog.bindingModels.CategoryBindingModel;
import bobi.blog.entities.Category;
import bobi.blog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return this.categoryRepository.getOne(id);
    }

    @Override
    public void addCategory(CategoryBindingModel categoryBindingModel) {
        Category category = new Category(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public void editCategory(Category category, CategoryBindingModel categoryBindingModel) {
        category.setName(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteCategory(Category category) {
        this.categoryRepository.delete(category);
    }
}
