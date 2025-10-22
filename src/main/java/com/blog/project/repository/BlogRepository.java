package com.blog.project.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.project.model.Article;

public interface BlogRepository extends JpaRepository<Article, UUID> {

}
