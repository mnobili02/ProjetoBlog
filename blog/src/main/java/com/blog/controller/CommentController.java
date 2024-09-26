package com.blog.controller;

import com.blog.entity.Comment;
import com.blog.entity.Posts;
import com.blog.repository.CommentRepository;
import com.blog.service.CommentService;
import org.hibernate.annotations.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService service;


    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAll(){
        List<Comment> comment = service.getAll();
        return ResponseEntity.ok().body(comment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(@PathVariable Long id){
        try {
            Comment comment = service.getById(id);
            return ResponseEntity.ok().body(comment);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> save(@Validated @RequestBody Comment comment){
        try{
            service.save(comment);
            return ResponseEntity.ok().body("Cadastrado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Validated  @PathVariable Long id, @RequestBody Comment comment){
        try{
            service.update(id, comment);
            return ResponseEntity.ok().body("Atualizado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@Validated @PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Deletado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = service.getCommentsByPostId(postId);
        return ResponseEntity.ok().body(comments);
    }
}
