package com.blog.service;

import com.blog.entity.Like;
import com.blog.entity.Posts;
import com.blog.entity.Users;
import com.blog.repository.LikeRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LikeService likeService;

    private Posts post;
    private Users user;
    private Like like;

    @BeforeEach
    void setUp() {
        post = new Posts();
        post.setId(1L);

        user = new Users();
        user.setId(1L);

        like = new Like();
        like.setId(1L);
        like.setPost(post);
        like.setUser(user);
        like.setLiked(true);
    }

    @Test
    void testGetLikesByPost_Success() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        when(likeRepository.findByPost(post)).thenReturn(Arrays.asList(like));

        List<Like> likes = likeService.getLikesByPost(1L);

        assertNotNull(likes);
        assertEquals(1, likes.size());
        assertEquals(like.getId(), likes.get(0).getId());

        verify(postRepository, times(1)).findById(1L);
        verify(likeRepository, times(1)).findByPost(post);
    }

    @Test
    void testGetLikesByPost_PostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            likeService.getLikesByPost(1L);
        });
        assertEquals("O post não existe!", exception.getMessage());
    }

    @Test
    void testLikePost_NewLike() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(likeRepository.findByPostAndUser(post, user)).thenReturn(Optional.empty());

        likeService.likePost(1L, 1L, true);

        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void testLikePost_UpdateLike() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(likeRepository.findByPostAndUser(post, user)).thenReturn(Optional.of(like));

        likeService.likePost(1L, 1L, false);

        assertFalse(like.isLiked());
        verify(likeRepository, times(1)).save(like);
    }

    @Test
    void testLikePost_PostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            likeService.likePost(1L, 1L, true);
        });
        assertEquals("O post não existe!", exception.getMessage());
    }

    @Test
    void testLikePost_UserNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            likeService.likePost(1L, 1L, true);
        });
        assertEquals("O usuário não existe!", exception.getMessage());
    }

    @Test
    void testDeleteLike() {
        likeService.deleteLike(1L);

        verify(likeRepository, times(1)).deleteById(1L);
    }
}
