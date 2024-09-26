package com.blog.controller;

import com.blog.entity.Comment;
import com.blog.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Adiciona a extensão do Mockito
public class CommentControllerTest {

    @Mock
    private CommentService service;

    @InjectMocks
    private CommentController controller;

    private Comment comment;

    @BeforeEach
    void setUp() {
        service = new CommentService();

        comment = new Comment();
        comment.setId(1L);
        comment.setComment("Test Comment");
    }

//    @Test
//    void testGetAll_Success() {
//        List<Comment> comments = new ArrayList<>();
//        comments.add(comment);
//        when(service.getAll()).thenReturn(comments);
//
//        ResponseEntity<List<Comment>> response = controller.getAll();
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(1, response.getBody().size());
//        assertEquals(comment.getId(), response.getBody().get(0).getId());
//    }

//    @Test
//    void testGetById_Success() {
//        when(service.getById(1L)).thenReturn(comment);
//
//        ResponseEntity<Comment> response = controller.getById(1L);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(comment.getId(), response.getBody().getId());
//    }

//    @Test
//    void testGetById_NotFound() {
//        when(service.getById(1L)).thenThrow(new IllegalArgumentException("Comment not found"));
//
//        ResponseEntity<Comment> response = controller.getById(1L);
//
//        assertEquals(404, response.getStatusCodeValue());
//    }

    @Test
    void testSave_Success() {
        ResponseEntity<String> response = controller.save(comment);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cadastrado com sucesso!", response.getBody());
    }

    @Test
    void testUpdate_Success() {
        ResponseEntity<String> response = controller.update(1L, comment);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Atualizado com sucesso!", response.getBody());
    }

    @Test
    void testDelete_Success() {
        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deletado com sucesso!", response.getBody());
    }

//    @Test
//    void testGetCommentsByPostId_Success() {
//        List<Comment> comments = new ArrayList<>();
//        comments.add(comment);
//        when(service.getCommentsByPostId(1L)).thenReturn(comments);
//
//        ResponseEntity<List<Comment>> response = controller.getCommentsByPostId(1L);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(1, response.getBody().size());
//        assertEquals(comment.getId(), response.getBody().get(0).getId());
//    }
}
