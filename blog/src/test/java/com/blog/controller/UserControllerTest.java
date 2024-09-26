package com.blog.controller;

import com.blog.entity.Users;
import com.blog.service.UserService;
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
public class UserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController userController;

    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1L);
        user.setUsername("SampleUser");
        // Set other necessary properties for user if needed
    }

    @Test
    void testGetAll_Success() {
        List<Users> usersList = new ArrayList<>();
        usersList.add(user);
        when(service.getAll()).thenReturn(usersList);

        ResponseEntity<List<Users>> response = userController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(user.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testGetById_Success() {
        when(service.getById(1L)).thenReturn(user);

        ResponseEntity<Users> response = userController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user.getId(), response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.getById(2L)).thenThrow(new RuntimeException("User not found"));

        ResponseEntity<Users> response = userController.getById(2L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testSave_Success() {
        ResponseEntity<String> response = userController.save(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Saved", response.getBody());
        verify(service).save(user);  // Verifica se o método do serviço foi chamado
    }

    @Test
    void testSave_BadRequest() {
        doThrow(new RuntimeException("Error saving user")).when(service).save(user);

        ResponseEntity<String> response = userController.save(user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error saving user", response.getBody());
    }

    @Test
    void testUpdate_Success() {
        ResponseEntity<String> response = userController.update(1L, user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated", response.getBody());
        verify(service).update(1L, user);  // Verifica se o método do serviço foi chamado
    }

    @Test
    void testUpdate_BadRequest() {
        doThrow(new RuntimeException("Error updating user")).when(service).update(1L, user);

        ResponseEntity<String> response = userController.update(1L, user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error updating user", response.getBody());
    }

    @Test
    void testDelete_Success() {
        ResponseEntity<String> response = userController.delete(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deleted", response.getBody());
        verify(service).delete(1L);  // Verifica se o método do serviço foi chamado
    }

    @Test
    void testDelete_BadRequest() {
        doThrow(new RuntimeException("Error deleting user")).when(service).delete(1L);

        ResponseEntity<String> response = userController.delete(1L);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error deleting user", response.getBody());
    }
}
