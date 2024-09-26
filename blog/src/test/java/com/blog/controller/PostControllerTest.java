package com.blog.controller;

import com.blog.entity.Posts;
import com.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService service;

    @InjectMocks
    private PostController postController;

    private Posts post;

    @BeforeEach
    void setUp() {
        post = new Posts();
        post.setId(1L);
        post.setTitle("Sample Post");
        post.setContent("This is a sample post.");
        // Set other necessary properties for post if needed
    }

    @Test
    void testGetAll_Success() {
        List<Posts> postsList = new ArrayList<>();
        postsList.add(post);
        when(service.getAll()).thenReturn(postsList);

        ResponseEntity<List<Posts>> response = postController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(post.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testGetById_Success() {
        when(service.getById(1L)).thenReturn(post);

        ResponseEntity<Posts> response = postController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(post.getId(), response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.getById(2L)).thenThrow(new RuntimeException("Post not found"));

        ResponseEntity<Posts> response = postController.getById(2L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testSave_Success() {
        ResponseEntity<String> response = postController.save(post);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cadastrado com sucesso!", response.getBody());
        verify(service).save(post);  // Verifica se o método do serviço foi chamado
    }

    @Test
    void testSave_BadRequest() {
        doThrow(new RuntimeException("Error saving post")).when(service).save(post);

        ResponseEntity<String> response = postController.save(post);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error saving post", response.getBody());
    }

    @Test
    void testUpdate_Success() {
        ResponseEntity<String> response = postController.update(1L, post);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Atualizado com sucesso!", response.getBody());
        verify(service).update(1L, post);  // Verifica se o método do serviço foi chamado
    }

    @Test
    void testUpdate_BadRequest() {
        doThrow(new RuntimeException("Error updating post")).when(service).update(1L, post);

        ResponseEntity<String> response = postController.update(1L, post);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error updating post", response.getBody());
    }

    @Test
    void testDelete_Success() {
        ResponseEntity<String> response = postController.delete(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deletado com sucesso!", response.getBody());
        verify(service).delete(1L);  // Verifica se o método do serviço foi chamado
    }

    @Test
    void testDelete_BadRequest() {
        doThrow(new RuntimeException("Error deleting post")).when(service).delete(1L);

        ResponseEntity<String> response = postController.delete(1L);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error deleting post", response.getBody());
    }
}
