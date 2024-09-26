package com.blog.controller;

import com.blog.entity.Posts;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {


    @Autowired
    private PostService service;

    @GetMapping("/all")
    public ResponseEntity<List<Posts>> getAll(){
        List<Posts> posts = service.getAll();
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Posts> getById(@PathVariable Long id){
        try {
            Posts post = service.getById(id);
            return ResponseEntity.ok().body(post);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        }

    @PostMapping
    public ResponseEntity<String> save(@Validated @RequestBody Posts posts){
        try{
            service.save(posts);
            return ResponseEntity.ok().body("Cadastrado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Validated  @PathVariable Long id, @RequestBody Posts posts){
        try{
            service.update(id, posts);
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

}
