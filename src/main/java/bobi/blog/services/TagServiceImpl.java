package bobi.blog.services;

import bobi.blog.entities.Tag;
import bobi.blog.repositories.TagRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag getTagById(Integer id) throws NotFoundException {
        if (!this.tagRepository.exists(id)) {
            throw new NotFoundException("Tag not found!");
        }

        return this.tagRepository.findOne(id);
    }


    @Override
    public Tag getTagByName(String name) throws NotFoundException {
        Tag tag = this.tagRepository.findByName(name);
        if (tag == null) {
            throw new NotFoundException("Tag not found!");
        }

        return tag;
    }

    @Override
    public Set<Tag> findTagsFromString(String tagString) {
        Set<Tag> tagSet = new HashSet<>();

        String[] tagNames = tagString.split(",\\s*");

        Tag currentTag;
        for (String tagName : tagNames) {
            try {
                currentTag = this.getTagByName(tagName);
            } catch (NotFoundException e) {
                currentTag = new Tag(tagName);
                this.tagRepository.saveAndFlush(currentTag);
            }
            tagSet.add(currentTag);
        }

        return tagSet;
    }
}
