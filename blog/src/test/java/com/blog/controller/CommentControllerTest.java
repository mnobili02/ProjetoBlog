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

@ExtendWith(MockitoExtension.class)
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
}
