package com.blog.project.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/* Data Transfer Object for Article.
   Decouples the web layer from the JPA Entity. */
public record ArticleDTO(
    UUID id,

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    String title,

    @NotBlank(message = "Content is required")
    String content,

    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author cannot be longer than 100 characters")
    String author,

    LocalDateTime updatedAt
) {}
