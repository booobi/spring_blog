package bobi.blog.controllers;

import bobi.blog.bindingModels.UserEditBingingModel;
import bobi.blog.entities.Role;
import bobi.blog.entities.User;
import bobi.blog.services.RoleService;
import bobi.blog.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminUserController(UserService userService,
                               RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String listUsers(Model model) {
        List<User> users = this.userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("view", "admin/user/list");

        return "base-layout";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) throws NotFoundException {
        User user = this.userService.getUserById(id);
        List<Role> roles = this.roleService.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "admin/user/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, UserEditBingingModel userEditBingingModel) throws NotFoundException {
        User user = this.userService.getUserById(id);

        this.userService.edit(user, userEditBingingModel);

        return "redirect:/admin/users/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) throws NotFoundException {
        User user = this.userService.getUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("view", "admin/user/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) throws NotFoundException {
        User user = this.userService.getUserById(id);

        this.userService.delete(user);

        return "redirect:/admin/users/";
    }
}
