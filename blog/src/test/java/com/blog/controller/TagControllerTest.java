package com.blog.controller;

import com.blog.entity.Tags;
import com.blog.service.TagService;
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
public class TagControllerTest {

    @Mock
    private TagService service;

    @InjectMocks
    private TagController tagController;

    private Tags tag;

    @BeforeEach
    void setUp() {
        tag = new Tags();
        tag.setId(1L);
        tag.setName("Sample Tag");
        // Set other necessary properties for tag if needed
    }

    @Test
    void testGetAll_Success() {
        List<Tags> tagsList = new ArrayList<>();
        tagsList.add(tag);
        when(service.getAll()).thenReturn(tagsList);

        ResponseEntity<List<Tags>> response = tagController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(tag.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testGetById_Success() {
        when(service.getById(1L)).thenReturn(tag);

        ResponseEntity<Tags> response = tagController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tag.getId(), response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.getById(2L)).thenThrow(new RuntimeException("Tag not found"));

        ResponseEntity<Tags> response = tagController.getById(2L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testSave_Success() {
        ResponseEntity<String> response = tagController.save(tag);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cadastrado com sucesso!", response.getBody());
        verify(service).save(tag);  // Verifica se o método do serviço foi chamado
    }

    @Test
    void testSave_BadRequest() {
        doThrow(new RuntimeException("Error saving tag")).when(service).save(tag);

        ResponseEntity<String> response = tagController.save(tag);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error saving tag", response.getBody());
    }

    @Test
    void testUpdate_Success() {
        ResponseEntity<String> response = tagController.update(1L, tag);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Atualizado com sucesso!", response.getBody());
        verify(service).update(1L, tag);  // Verifica se o método do serviço foi chamado
    }

    @Test
    void testUpdate_BadRequest() {
        doThrow(new RuntimeException("Error updating tag")).when(service).update(1L, tag);

        ResponseEntity<String> response = tagController.update(1L, tag);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error updating tag", response.getBody());
    }

    @Test
    void testDelete_Success() {
        ResponseEntity<String> response = tagController.delete(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deletado com sucesso!", response.getBody());
        verify(service).delete(1L);  // Verifica se o método do serviço foi chamado
    }

    @Test
    void testDelete_BadRequest() {
        doThrow(new RuntimeException("Error deleting tag")).when(service).delete(1L);

        ResponseEntity<String> response = tagController.delete(1L);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error deleting tag", response.getBody());
    }
}
