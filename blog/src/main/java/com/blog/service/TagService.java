package com.blog.service;

import com.blog.entity.Posts;
import com.blog.entity.Tags;
import com.blog.entity.Users;
import com.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository repository;

    public List<Tags> getAll() {
        return repository.findAll();
    }

    public Tags getById(Long id) {
        Tags tags = repository.findById(id).orElse(null);
        Assert.notNull(tags, "Tags not found");
        return tags;
    }

    public void save(Tags tags){
        Tags tag = repository.save(tags);
    }

    public void update(Long id, Tags tags){
        Tags tag = repository.findById(id).orElse(null);
        Assert.notNull(tag, "Tag não existe");
        repository.save(tags);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
