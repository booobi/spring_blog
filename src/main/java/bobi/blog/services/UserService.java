package bobi.blog.services;

import bobi.blog.bindingModels.UserBindingModel;
import bobi.blog.entities.User;
import bobi.blog.viewModels.UserViewModel;
import javassist.NotFoundException;

public interface UserService {
    User getUserById(Integer id) throws NotFoundException;
    User getCurrentUser();

    UserViewModel register(UserBindingModel bindingModel);
}
