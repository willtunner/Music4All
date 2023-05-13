package com.music4all.Music4All.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerImageNotFound(ImageNotFoundException e) {
        return "Image não encontrada";
    }
}
