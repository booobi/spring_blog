package bobi.blog.services;

import bobi.blog.entities.Tag;
import javassist.NotFoundException;

import java.util.Set;

public interface TagService {
    Tag getTagById(Integer id) throws NotFoundException;

    Tag getTagByName(String name) throws NotFoundException;

    Set<Tag> findTagsFromString(String tagString) throws NotFoundException;
}
