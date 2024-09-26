package com.blog.service;

import com.blog.entity.Comment;
import com.blog.entity.Posts;
import com.blog.entity.Users;
import com.blog.repository.CommentRepository;
import com.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;

    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1L);
        user.setUsername("username");

        comment = new Comment();
        comment.setId(1L);
        comment.setComment("Test Comment");
        comment.setPost(new Posts());
        comment.setUser(user);

        user.setComments(Arrays.asList(comment));
    }

    @Test
    void testGetAll() {
        when(commentRepository.findAll()).thenReturn(Arrays.asList(comment));

        List<Comment> comments = commentService.getAll();

        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals(comment.getId(), comments.get(0).getId());

        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void testGetById_Success() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment foundComment = commentService.getById(1L);

        assertNotNull(foundComment);
        assertEquals(comment.getId(), foundComment.getId());

        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.getById(1L);
        });
        assertEquals("O comentário não existe! ", exception.getMessage());
    }

    @Test
    void testSave_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.save(comment);
        });
        assertEquals("O usúario não existe! ", exception.getMessage());
    }

    @Test
    void testUpdate_Success() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        commentService.update(1L, comment);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testUpdate_NotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.update(1L, comment);
        });
        assertEquals("Post não existe", exception.getMessage());
    }

    @Test
    void testGetCommentsByPostId() {
        when(commentRepository.findByPostId(1L)).thenReturn(Arrays.asList(comment));

        List<Comment> comments = commentService.getCommentsByPostId(1L);

        assertNotNull(comments);
        assertEquals(1, comments.size());

        verify(commentRepository, times(1)).findByPostId(1L);
    }

    @Test
    void testDelete() {
        commentService.delete(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }
}
