package bobi.blog.Controller;

import bobi.blog.entities.User;
import bobi.blog.repository.ArticleRepository;
import bobi.blog.repository.RoleRepository;
import bobi.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public String listUsers(Model model){
        List<User> userList = this.userRepository.findAll();

        model.addAttribute("users", userList);
        model.addAttribute("view", "admin/user/list");

        return "base-layout";
    }
}
