package bobi.blog.services;

import bobi.blog.bindingModels.UserBindingModel;
import bobi.blog.entities.Role;
import bobi.blog.entities.User;
import bobi.blog.repositories.UserRepository;
import bobi.blog.viewModels.UserViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @Override
    public User getUserById(Integer id) throws NotFoundException {
        User user = this.userRepository.findOne(id);
        if (user == null){
            throw new NotFoundException("Invalid User ID!");
        }
        return user;
    }

    @Override
    public User getCurrentUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findByEmail(principal.getUsername());
    }

    @Override
    public UserViewModel register(UserBindingModel bindingModel) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User(bindingModel.getEmail(), bindingModel.getFullName(), bCryptPasswordEncoder.encode(bindingModel.getPassword()));
        Role userRole = this.roleService.getUserRole();
        user.addRole(userRole);
        UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
        return userViewModel;
    }
}
