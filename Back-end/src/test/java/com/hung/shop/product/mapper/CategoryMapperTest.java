package com.hung.shop.product.mapper;

import com.hung.shop.product.dto.category.response.CategoryDto;
import com.hung.shop.product.entity.Category;
import com.hung.shop.share.CategoryPOJO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class CategoryMapperTest {
    @Autowired
    private CategoryMapper categoryMapper;
    @Test
    void shouldMapEntityToDto() {

        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Category");
        category.setDescription("Test Description");
        category.setImage("test.jpg");
        category.setCreatedAt(LocalDateTime.now());

        CategoryDto dto = categoryMapper.toCategoryDto(category);

        assertEquals(1L, dto.getId());
        assertEquals("Test Category", dto.getCategoryName());
        assertEquals("Test Description", dto.getDescription());
        assertTrue(dto.getImage().contains("test.jpg"));
        assertNotNull(dto.getCreatedAt());
    }
    @Test
    void shouldMapEntityToPoJo(){
        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Category");
        category.setDescription("Test Description");
        category.setImage("test.jpg");
        category.setCreatedAt(LocalDateTime.now());

        CategoryPOJO pojo = categoryMapper.toCategoryPOJO(category);

        assertEquals(1L, pojo.getId());
        assertEquals("Test Category", pojo.getCategoryName());
        assertEquals("Test Description", pojo.getDescription());
        assertTrue(pojo.getImage().contains("test.jpg"));
        assertNotNull(pojo.getCreatedAt());
    }
}
