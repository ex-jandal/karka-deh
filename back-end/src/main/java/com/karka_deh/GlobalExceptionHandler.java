package com.karka_deh;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.karka_deh.errors.*;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // handle @Valid errors
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return new ResponseEntity<>(Map.of("error", errors), HttpStatus.BAD_REQUEST);
  }

  // handle entity not found
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(NoSuchElementException ex) {
    return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  //
  // custom errors
  //
  @ExceptionHandler({
      PostNotOwnedByUser.class,
      BadSearchQueryException.class,
      UserNotFoundException.class,
      SlugNotFoundException.class,
      SlugAlreadExistsException.class
  })
  public ResponseEntity<?> handlePostNotOwnedByUser(RuntimeException ex) {
    HttpStatus httpStatus = null;

    switch (ex) {
      case PostNotOwnedByUser _ -> httpStatus = HttpStatus.FORBIDDEN;
      case BadSearchQueryException _ -> httpStatus = HttpStatus.BAD_REQUEST;
      case UserNotFoundException _ -> httpStatus = HttpStatus.NOT_FOUND;
      case SlugNotFoundException _ -> httpStatus = HttpStatus.NOT_FOUND;
      case SlugAlreadExistsException _ -> httpStatus = HttpStatus.CONFLICT;
      default -> httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    ;
    return ResponseEntity.status(httpStatus)
        .body(Map.of("error", ex.getMessage()));
  }

  // catch-all fallback
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {

    return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
