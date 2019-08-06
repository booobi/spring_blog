package bobi.blog.controllers;

import bobi.blog.entities.Tag;
import bobi.blog.repositories.TagRepository;
import bobi.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping("/tag/{name}")
    private String articlesWithTag(@PathVariable String name, Model model) {
        Tag tag = this.tagService.getTagByName(name);

        if(tag == null) {
            return "redirect:/";
        }

        model.addAttribute("tag", tag);
        model.addAttribute("view", "tag/articles");

        return "base-layout";
    }

}
