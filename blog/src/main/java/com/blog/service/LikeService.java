package com.blog.service;

import com.blog.entity.Like;
import com.blog.entity.Posts;
import com.blog.entity.Users;
import com.blog.repository.LikeRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Like> getLikesByPost(Long postId) {
        Posts post = postRepository.findById(postId).orElse(null);
        Assert.notNull(post, "O post não existe!");
        return likeRepository.findByPost(post);
    }

    public void likePost(Long postId, Long userId, boolean liked) {
        Posts post = postRepository.findById(postId).orElse(null);
        Users user = userRepository.findById(userId).orElse(null);

        Assert.notNull(post, "O post não existe!");
        Assert.notNull(user, "O usuário não existe!");

        Like like = likeRepository.findByPostAndUser(post, user).orElse(new Like());
        like.setPost(post);
        like.setUser(user);
        like.setLiked(liked);

        likeRepository.save(like);
    }

    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }
}
