package bobi.blog.services;

import bobi.blog.entities.Article;
import bobi.blog.entities.User;
import bobi.blog.models.bindingModels.UserBindingModel;
import bobi.blog.models.bindingModels.UserEditBingingModel;
import bobi.blog.models.viewModels.UserViewModel;
import javassist.NotFoundException;

import java.util.List;

public interface UserService {
    User getUserById(Integer id) throws NotFoundException;

    User getCurrentUser();

    void delete(User user);

    void edit(User user, UserEditBingingModel userEditBingingModel);

    boolean isUserAuthorOrAdmin(Article article);

    List<User> getAllUsers();

    UserViewModel register(UserBindingModel bindingModel) throws Exception;
}
