package bobi.blog.controllers;

import bobi.blog.bindingModels.UserBindingModel;
import bobi.blog.config.Consts;
import bobi.blog.entities.User;
import bobi.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("view", "user/register");

        return Consts.BASE_LAYOUT;
    }

    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel) throws Exception {
        this.userService.register(userBindingModel);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("view", "user/login");

        return Consts.BASE_LAYOUT;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        //TODO: move (getAuthentication) to user service?
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model) {
        User user = this.userService.getCurrentUser();

        model.addAttribute("user", user);
        model.addAttribute("view", "user/profile");
        return Consts.BASE_LAYOUT;
    }
}
