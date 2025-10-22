package com.blog.project.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @PostMapping("/add")
    public String createArticle(@ModelAttribute("article") Article article, RedirectAttributes ra) {
        repo.save(article);
        ra.addFlashAttribute("message", "Article Created");
        return "redirect:/";
    }

    // Show Update Article Form
    @GetMapping("/edit/{id}")
    public String editArticleForm(@PathVariable UUID id, Model model) {
        // Optional<Article> article = repo.findById(id); -> this is valid too.. without exception handling

        Article article = repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found"));
        model.addAttribute("article", article);
        return "update";
    }

    // Update Article 
    @PutMapping("/edit/{id}")
    public String updateArticle(@PathVariable UUID id, @ModelAttribute("article") Article form, RedirectAttributes ra) {
        Article existing = repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found"));

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
        repo.deleteById(id);
        ra.addFlashAttribute("message", "Article Deleted");
        return "redirect:/";
    }

}
