package com.blog.project.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.project.dto.ArticleDTO;
import com.blog.project.exception.ArticleNotFoundException;
import com.blog.project.model.Article;
import com.blog.project.repository.BlogRepository;

@Service
public class ArticleService {

    @Autowired
    private BlogRepository repo;

    /**
     * PRIVATE HELPER METHOD (DRY Principle)
     * Centralized logic to fetch an article or throw an exception.
     */
    private Article findArticleById(UUID id) {
        // Fix: Explicitly enforce non-null for 'findById'
        return repo.findById(Objects.requireNonNull(id))
            .orElseThrow(() -> new ArticleNotFoundException("Article not found with ID: " + id));
    }

    // Converts Entity to DTO
    public ArticleDTO convertToDto(Article article) {
        return new ArticleDTO(
            article.getId(),
            article.getTitle(),
            article.getContent(),
            article.getAuthor(),
            article.getUpdatedAt()
        );
    }

    // Converts DTO to Entity for saving
    public Article convertToEntity(ArticleDTO dto) {
        Article article = new Article();
        article.setId(dto.id());
        article.setTitle(dto.title());
        article.setContent(dto.content());
        article.setAuthor(dto.author());
        return article;
    }

    // Get All Articles
    public List<ArticleDTO> getAllArticles() {
        return repo.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    // Get Article by ID
    public ArticleDTO getArticleById(UUID id) {
        Article article = findArticleById(id); // Reuse helper method
        return convertToDto(article);
    }

    // Create or Update Article
    public void saveArticle(ArticleDTO dto) {
        // Fix: Explicitly enforce non-null to satisfy 'save' contract
        Article entity = convertToEntity(Objects.requireNonNull(dto));
        repo.save(entity);
    }

    // Update Article
    public void updateArticle(UUID id, ArticleDTO dto) {
        Article existing = findArticleById(id); // Reuse helper method

        existing.setTitle(dto.title());
        existing.setAuthor(dto.author());
        existing.setContent(dto.content());
        
        repo.save(existing);
    }

    // Delete Article
    public void deleteArticle(UUID id) {
        Article article = findArticleById(id); // Reuse helper method
        // Fix: Explicitly enforce non-null for 'delete'
        repo.delete(Objects.requireNonNull(article));
    }
}