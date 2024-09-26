package com.blog.service;

import com.blog.entity.Tags;
import com.blog.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    private Tags tag;

    @BeforeEach
    void setUp() {
        tag = new Tags();
        tag.setId(1L);
        tag.setName("Test Tag");
    }

    @Test
    void testGetAllTags_Success() {
        when(tagRepository.findAll()).thenReturn(List.of(tag));

        List<Tags> tags = tagService.getAll();

        assertNotNull(tags);
        assertEquals(1, tags.size());
        assertEquals(tag.getId(), tags.get(0).getId());

        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void testGetTagById_Success() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        Tags foundTag = tagService.getById(1L);

        assertNotNull(foundTag);
        assertEquals(tag.getId(), foundTag.getId());

        verify(tagRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTagById_NotFound() {
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tagService.getById(1L);
        });
        assertEquals("Tags not found", exception.getMessage());
    }

    @Test
    void testSaveTag_Success() {
        when(tagRepository.save(any(Tags.class))).thenReturn(tag);

        tagService.save(tag);

        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void testUpdateTag_Success() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        tagService.update(1L, tag);

        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void testUpdateTag_NotFound() {
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tagService.update(1L, tag);
        });
        assertEquals("Tag não existe", exception.getMessage());
    }

    @Test
    void testDeleteTag_Success() {
        doNothing().when(tagRepository).deleteById(1L);

        tagService.delete(1L);

        verify(tagRepository, times(1)).deleteById(1L);
    }
}
