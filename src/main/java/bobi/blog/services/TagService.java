package bobi.blog.services;

import bobi.blog.entities.Tag;

public interface TagService {
    Tag getTagById(Integer id);
    Tag getTagByName(String name);
}
