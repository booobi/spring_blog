package bobi.blog.services;

import bobi.blog.entities.Category;
import bobi.blog.models.bindingModels.CategoryBindingModel;
import bobi.blog.repositories.CategoryRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Category getCategoryById(Integer id) throws NotFoundException {
        if (!this.categoryRepository.exists(id)) {
            throw new NotFoundException("Category not found!");
        }

        return this.categoryRepository.findOne(id);
    }

    @Override
    public void create(CategoryBindingModel categoryBindingModel) {
        Category category = new Category(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public void update(Category category, CategoryBindingModel categoryBindingModel) {
        category.setName(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public void delete(Category category) {
        this.categoryRepository.delete(category);
    }
}
