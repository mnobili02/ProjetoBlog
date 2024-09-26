package com.blog.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.blog.controller.CommentController;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = new Comment();
        comment.setId(1L);
        comment.setComment("Test Comment");
    }

    @Test
    void testGetAll() {
        List<Comment> comments = Arrays.asList(comment);
        when(commentService.getAll()).thenReturn(comments);

        ResponseEntity<List<Comment>> response = commentController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(comments, response.getBody());
    }

    @Test
    void testGetById() {
        when(commentService.getById(1L)).thenReturn(comment);

        ResponseEntity<Comment> response = commentController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(comment, response.getBody());
    }

    @Test
    void testSave() {
        doNothing().when(commentService).save(any(Comment.class));

        ResponseEntity<String> response = commentController.save(comment);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cadastrado com sucesso!", response.getBody());
    }

    @Test
    void testUpdate() {
        doNothing().when(commentService).update(eq(1L), any(Comment.class));

        ResponseEntity<String> response = commentController.update(1L, comment);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Atualizado com sucesso!", response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(commentService).delete(1L);

        ResponseEntity<String> response = commentController.delete(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deletado com sucesso!", response.getBody());
    }

    @Test
    void testGetCommentsByPostId() {
        List<Comment> comments = Arrays.asList(comment);
        when(commentService.getCommentsByPostId(1L)).thenReturn(comments);

        ResponseEntity<List<Comment>> response = commentController.getCommentsByPostId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(comments, response.getBody());
    }
}
