package com.blog.controller;

import com.blog.entity.Like;
import com.blog.service.LikeService;
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
public class LikeControllerTest {

    @Mock
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

    private Like like;

    @BeforeEach
    void setUp() {
        like = new Like();
        like.setId(1L);
        // Set other necessary properties for like if needed
    }

    @Test
    void testGetLikesByPost_Success() {
        List<Like> likes = new ArrayList<>();
        likes.add(like);
        when(likeService.getLikesByPost(1L)).thenReturn(likes);

        ResponseEntity<List<Like>> response = likeController.getLikesByPost(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(like.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testLikePost_Success() {
        ResponseEntity<String> response = likeController.likePost(1L, 1L, true);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ação realizada com sucesso!", response.getBody());
        verify(likeService).likePost(1L, 1L, true);  // Verifica se o método do serviço foi chamado
    }

    @Test
    void testDeleteLike_Success() {
        ResponseEntity<String> response = likeController.deleteLike(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Like deletado com sucesso!", response.getBody());
        verify(likeService).deleteLike(1L);  // Verifica se o método do serviço foi chamado
    }
}
