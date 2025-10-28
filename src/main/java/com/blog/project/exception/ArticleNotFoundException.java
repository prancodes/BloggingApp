package com.blog.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This annotation is optional, but good practice.
// It ensures that even if the @ExceptionHandler fails, Spring will still return a 404.
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
