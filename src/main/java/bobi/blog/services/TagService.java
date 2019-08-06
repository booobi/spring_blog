package bobi.blog.services;

import bobi.blog.entities.Tag;

import java.util.Set;

public interface TagService {
    Tag getTagById(Integer id);
    Tag getTagByName(String name);
    Set<Tag> findTagsFromString(String tagString);
}
