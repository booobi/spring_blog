package bobi.blog.Controller;

import bobi.blog.entities.Tag;
import bobi.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TagController {
    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/tag/{name}")
    private String articlesWithTag(@PathVariable String name, Model model) {
        Tag tag = this.tagRepository.findByName(name);

        if(tag == null) {
            return "redirect:/";
        }

        model.addAttribute("tag", tag);
        model.addAttribute("view", "tag/articles");

        return "base-layout";
    }

}
