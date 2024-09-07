package de.telran.myshop.errors;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ShopExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {CommentsException.class})
    protected ResponseEntity<Object> handleCommentsExceptions(
            CommentsException ex,
            WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getBody());
        errors.put("comment_id", ""+ex.getCommentId());
        errors.put("path", request.getDescription(false));
        return handleExceptionInternal(
                ex,
                errors,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {CardsException.class})
    protected ResponseEntity<Object> handleCardsExceptions(
            CardsException ex,
            WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        errors.put("comment_id", ""+ex.getId());
        errors.put("path", request.getDescription(false));
        return handleExceptionInternal(
                ex,
                errors,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex,
            WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(
                violation -> {
                    errors.put(
                            violation.getPropertyPath().toString(),
                            violation.getMessage()
                    );
                }
        );
        return handleExceptionInternal(
                ex,
                errors,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}
