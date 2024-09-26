package com.blog.service;

import com.blog.entity.Comment;
import com.blog.entity.Posts;
import com.blog.entity.Users;
import com.blog.repository.CommentRepository;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private UserRepository userRepository;

    public List<Comment> getAll() {
        return repository.findAll();
    }

    public Comment getById(Long id) {
        Comment comment = repository.findById(id).orElse(null);
        Assert.notNull(comment, "O comentário não existe! ");
        return comment;
    }

    public void save(Comment comment){
        Users users = userRepository.findById(comment.getUser().getId()).orElse(null);
        Assert.notNull(users, "O usúario não existe! ");

        users.getComments().add(comment);

        repository.save(comment);
        userRepository.save(users);
    }

    public void update(Long id, Comment comment){
        Comment comment1 = repository.findById(id).orElse(null);
        Assert.notNull(comment1, "Post não existe");
        repository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        Assert.notNull(postId, "O id do post não pode ser nulo!");
        return repository.findByPostId(postId);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }


}
