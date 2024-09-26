package com.blog.controller;

import com.blog.entity.Posts;
import com.blog.entity.Tags;
import com.blog.repository.TagRepository;
import com.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {


    @Autowired
    private TagService service;

    @GetMapping("/all")
    public ResponseEntity<List<Tags>> getAll(){
        List<Tags> list = service.getAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tags> getById(@PathVariable Long id){
        try {
            Tags tags = service.getById(id);
            return ResponseEntity.ok().body(tags);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }    }

    @PostMapping
    public ResponseEntity<String> save(@Validated @RequestBody Tags tags){
        try{
            service.save(tags);
            return ResponseEntity.ok().body("Cadastrado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> update(@Validated @PathVariable Long id, @RequestBody Tags tags){
        try{
            service.update(id, tags);
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
