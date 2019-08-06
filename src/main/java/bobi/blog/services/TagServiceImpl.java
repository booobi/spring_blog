package bobi.blog.services;

import bobi.blog.entities.Tag;
import bobi.blog.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService{

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag getTagById(Integer id) {
        return this.tagRepository.findOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return this.tagRepository.findByName(name);
    }

    @Override
    public Set<Tag> findTagsFromString(String tagString) {
        Set<Tag> tagSet = new HashSet<>();

        String[] tagNames = tagString.split(",\\s*");

        for (String tagName : tagNames) {
            Tag currentTag = this.getTagByName(tagName);
            if (currentTag == null) {
                currentTag = new Tag(tagName);
                this.tagRepository.saveAndFlush(currentTag);
            }
            tagSet.add(currentTag);
        }

        return tagSet;
    }
}
