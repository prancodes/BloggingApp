package com.blog.project.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the specific case where an article is not found (404).
     */
    @ExceptionHandler(ArticleNotFoundException.class)
    public String handleArticleNotFound(ArticleNotFoundException ex, Model model) {
        model.addAttribute("status", "404 Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/error-message";  // Renders the new error-page.html
    }

    /**
     * Handles the "No static resource" error you saw (404).
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFound(NoResourceFoundException ex, Model model) {
        model.addAttribute("status", "404 Not Found");
        model.addAttribute("errorMessage", "The page or resource you are looking for does not exist.");
        return "error/error-message";
    }

    /**
     * A general-purpose handler for all other exceptions (500).
     * This will catch any other error in your application.
     */
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        ex.printStackTrace(); // Good for logging during development
        model.addAttribute("status", "500 Internal Server Error");
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        return "error/error-message";
    }

}
