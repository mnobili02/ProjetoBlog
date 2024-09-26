package com.blog.controller;

import com.blog.entity.Like;
import com.blog.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Like>> getLikesByPost(@PathVariable Long postId) {
        List<Like> likes = likeService.getLikesByPost(postId);
        return ResponseEntity.ok().body(likes);
    }

    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<String> likePost(
            @PathVariable Long postId,
            @PathVariable Long userId,
            @RequestParam boolean liked) {
        likeService.likePost(postId, userId, liked);
        return ResponseEntity.ok().body("Ação realizada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id);
        return ResponseEntity.ok().body("Like deletado com sucesso!");
    }
}
