package com.blog.service;

import com.blog.controller.PostController;
import com.blog.entity.Posts;
import com.blog.entity.Tags;
import com.blog.entity.Users;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private UserRepository userRepository;

    public List<Posts> getAll() {
        return repository.findAll();
    }

    public Posts getById(Long id) {
        Posts posts = repository.findById(id).orElse(null);
        Assert.notNull(posts, "O Post não existe! ");
        return posts;
    }

    public void save(Posts posts){

        Users users = userRepository.findById(posts.getUsers().getId()).orElse(null);
        Assert.notNull(users, "Users not found");

        users.getPosts().add(posts);

        repository.save(posts);
        userRepository.save(users);
    }

    public void update(Long id, Posts posts){
        Posts post = repository.findById(id).orElse(null);
        Assert.notNull(post, "Post não existe");
        posts.setId(id);
        repository.save(posts);
    }

    public void delete(Long id){
        Posts post = repository.findById(id).orElse(null);

        assert post != null;
        Users users = userRepository.findById(post.getUsers().getId()).orElse(null);
        Assert.notNull(users, "Users not found");

        users.getPosts().remove(post);
        userRepository.save(users);

        repository.deleteById(id);
    }
}
