package com.blog.controller;

import com.blog.entity.Posts;
import com.blog.entity.Users;
import com.blog.repository.UserRepository;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;


    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAll() {
        List<Users> users =  service.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getById(@PathVariable Long id) {
        try {
            Users users = service.getById(id);
            return ResponseEntity.ok().body(users);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> save(@Validated @RequestBody Users user) {
        try{
            service.save(user);
            return ResponseEntity.ok("Saved");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> update(@Validated @PathVariable Long id, @RequestBody Users user) {
        try{
            service.update(id, user);
            return ResponseEntity.ok("Updated");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@Validated @PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
