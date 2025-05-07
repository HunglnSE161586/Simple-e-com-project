package com.hung.shop.share;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CategoryPOJO {
    private Long id;
    private String categoryName;
    private String description;
    private LocalDateTime createdAt;
    private String image;
}
