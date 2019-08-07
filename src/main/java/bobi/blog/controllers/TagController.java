package bobi.blog.controllers;

import bobi.blog.config.Consts;
import bobi.blog.entities.Tag;
import bobi.blog.services.TagService;
import javassist.NotFoundException;
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
    private String articlesWithTag(@PathVariable String name, Model model) throws NotFoundException {
        Tag tag = this.tagService.getTagByName(name);

        model.addAttribute("tag", tag);
        model.addAttribute("view", "tag/articles");

        return Consts.BASE_LAYOUT;
    }

}
