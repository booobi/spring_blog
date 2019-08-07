package bobi.blog.services;

import bobi.blog.entities.Article;
import bobi.blog.entities.Comment;
import bobi.blog.entities.Role;
import bobi.blog.entities.User;
import bobi.blog.models.bindingModels.UserBindingModel;
import bobi.blog.models.bindingModels.UserEditBingingModel;
import bobi.blog.models.viewModels.UserViewModel;
import bobi.blog.repositories.UserRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ArticleService articleService, CommentService commentService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.articleService = articleService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public User getUserById(Integer id) throws NotFoundException {
        User user = this.userRepository.findOne(id);
        if (user == null) {
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
    public void delete(User user) {
        Set<Article> articles = user.getArticles();
        for (Article art : articles) {
            //delete all comments in the article
            Set<Comment> comments = art.getComments();
            for (Comment comment : comments) {
                this.commentService.delete(comment);
            }
            this.articleService.delete(art);
        }

        //delete all comments made by user
        Set<Comment> comments = user.getComments();
        for (Comment comment : comments) {
            this.commentService.delete(comment);
        }

        this.userRepository.delete(user);
    }

    @Override
    public void edit(User user, UserEditBingingModel userEditBingingModel) {
        if (!StringUtils.isEmpty(userEditBingingModel.getPassword()) && !StringUtils.isEmpty(userEditBingingModel.getConfirmPassword())) {
            if (userEditBingingModel.getPassword().equals(userEditBingingModel.getConfirmPassword())) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                user.setPassword(bCryptPasswordEncoder.encode(userEditBingingModel.getPassword()));
            }
        }
        user.setFullName(userEditBingingModel.getFullName());
        user.setEmail(userEditBingingModel.getEmail());

        Set<Role> rolesToSet = new HashSet<>();
        for (Integer roleId : userEditBingingModel.getRoles()) {
            rolesToSet.add(this.roleService.getRoleById(roleId));
        }
        user.setRoles(rolesToSet);

        this.userRepository.save(user);
    }

    @Override
    public boolean isUserAuthorOrAdmin(Article article) {
        User user = getCurrentUser();

        return user.isAdmin() || user.isAuthor(article);
    }


    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserViewModel register(UserBindingModel bindingModel) throws Exception {

        if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            throw new Exception("Password mismatch!");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User(bindingModel.getEmail(), bindingModel.getFullName(), bCryptPasswordEncoder.encode(bindingModel.getPassword()));
        Role userRole = this.roleService.getUserRole();
        user.addRole(userRole);

        this.userRepository.saveAndFlush(user);

        UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
        return userViewModel;
    }
}
