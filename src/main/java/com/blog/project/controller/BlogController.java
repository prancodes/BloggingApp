package com.blog.project.controller;

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

import com.blog.project.dto.ArticleDTO;
import com.blog.project.service.ArticleService;

@Controller
public class BlogController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "index";
    }

    // Show Add Article Form
    @GetMapping("/new")
    public String addArticleForm(Model model) {
        model.addAttribute("article", new ArticleDTO(null, "", "", "", null));
        return "add";
    }

    // Create new Article
    @PostMapping("/new")
    public String createArticle(@Valid @ModelAttribute("article") ArticleDTO articleDto, BindingResult result, RedirectAttributes ra) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "add";  // Return to the 'add' form if there are errors
        }
        articleService.saveArticle(articleDto);
        ra.addFlashAttribute("message", "Article Created");
        return "redirect:/";
    }

    // Show Update Article Form
    @GetMapping("/edit/{id}")
    public String editArticleForm(@PathVariable UUID id, Model model) {
        ArticleDTO articleDto = articleService.getArticleById(id);
        model.addAttribute("article", articleDto);
        return "update";
    }

    // Update Article 
    @PutMapping("/edit/{id}")
    public String updateArticle(@PathVariable UUID id, @Valid @ModelAttribute("article") ArticleDTO form, BindingResult result, RedirectAttributes ra) {
        // Check for validation errors
        if(result.hasErrors()) {
            return "update";  // Return to the 'update' form if there are errors
        }
        articleService.updateArticle(id, form);
        ra.addFlashAttribute("message", "Article updated");

        return "redirect:/";
    }

    // Delete Article
    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable UUID id, RedirectAttributes ra) {
        articleService.deleteArticle(id);
        ra.addFlashAttribute("message", "Article Deleted");
        return "redirect:/";
    }

}
