package com.hung.shop.share;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CategoryPOJO {
    private Long categoryId;
    private String categoryName;
    private String description;
    private LocalDateTime createdAt;
}
