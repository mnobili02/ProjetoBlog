package com.blog.repository;

import com.blog.entity.Like;
import com.blog.entity.Posts;
import com.blog.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByPost(Posts post);

    Optional<Like> findByPostAndUser(Posts post, Users user);
}
