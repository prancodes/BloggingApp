package com.blog.project.controller;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import validation classes
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import com.blog.project.exception.ArticleNotFoundException;
import com.blog.project.model.Article;
import com.blog.project.repository.BlogRepository;

@Controller
public class BlogController {

    @Autowired
    private BlogRepository repo;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("articles", repo.findAll());
        return "index";
    }

    // Show Add Article Form
    @GetMapping("/new")
    public String addArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "add";
    }

    // Create new Article
    @PostMapping("/new")
    public String createArticle(@Valid @ModelAttribute("article") Article article, BindingResult result, RedirectAttributes ra) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "add";  // Return to the 'add' form if there are errors
        }
        // Fix: Explicitly enforce non-null to satisfy 'save' contract
        repo.save(Objects.requireNonNull(article));
        ra.addFlashAttribute("message", "Article Created");
        return "redirect:/";
    }

    // Show Update Article Form
    @GetMapping("/edit/{id}")
    public String editArticleForm(@PathVariable UUID id, Model model) {
        // Fix: Explicitly enforce non-null for 'findById'
        Article article = repo.findById(Objects.requireNonNull(id))
            .orElseThrow(() -> new ArticleNotFoundException("Article not found with ID: " + id));

        model.addAttribute("article", article);
        return "update";
    }

    // Update Article 
    @PutMapping("/edit/{id}")
    public String updateArticle(@PathVariable UUID id, @Valid @ModelAttribute("article") Article form, BindingResult result, RedirectAttributes ra) {

        // Check for validation errors
        if(result.hasErrors()) {
            return "update";  // Return to the 'update' form if there are errors
        }
        
        // Fix: Explicitly enforce non-null for 'findById'
        Article existing = repo.findById(Objects.requireNonNull(id))
            .orElseThrow(() -> new ArticleNotFoundException("Article not found with ID: " + id));

        existing.setTitle(form.getTitle());
        existing.setAuthor(form.getAuthor());
        existing.setContent(form.getContent());

        repo.save(existing);
        ra.addFlashAttribute("message", "Article updated");

        return "redirect:/";
    }

    // Delete Article
    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable UUID id, RedirectAttributes ra) {

        // Fix: Explicitly enforce non-null for 'findById'
        Article article = repo.findById(Objects.requireNonNull(id))
            .orElseThrow(() -> new ArticleNotFoundException("Article not found with ID: " + id));
        
        // Fix: Explicitly enforce non-null for 'delete'
        repo.delete(Objects.requireNonNull(article));
        ra.addFlashAttribute("message", "Article Deleted");
        return "redirect:/";
    }

}
