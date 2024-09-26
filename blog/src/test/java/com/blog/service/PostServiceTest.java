package com.blog.service;

import com.blog.entity.Posts;
import com.blog.entity.Users;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private Users user;
    private Posts post;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1L);
        user.setPosts(new ArrayList<>());

        post = new Posts();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setContent("This is a test post");
        post.setUsers(user);
    }

    @Test
    void testGetAllPosts_Success() {
        when(postRepository.findAll()).thenReturn(List.of(post));
        List<Posts> posts = postService.getAll();
        assertNotNull(posts);
        assertEquals(1, posts.size());
        assertEquals(post.getId(), posts.get(0).getId());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testGetPostById_Success() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        Posts foundPost = postService.getById(1L);
        assertNotNull(foundPost);
        assertEquals(post.getId(), foundPost.getId());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPostById_NotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.getById(1L);
        });
        assertEquals("O Post não existe! ", exception.getMessage());
    }

    @Test
    void testSavePost_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        postService.save(post);
        assertEquals(1, user.getPosts().size());
        verify(postRepository, times(1)).save(post);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSavePost_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.save(post);
        });
        assertEquals("Users not found", exception.getMessage());
    }

    @Test
    void testUpdatePost_Success() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        postService.update(1L, post);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testUpdatePost_NotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.update(1L, post);
        });
        assertEquals("Post não existe", exception.getMessage());
    }

    @Test
    void testDeletePost_Success() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        postService.delete(1L);
        assertTrue(user.getPosts().isEmpty());
        verify(postRepository, times(1)).deleteById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeletePost_UserNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.delete(1L);
        });
        assertEquals("Users not found", exception.getMessage());
    }
}
