package org.pccwg.test.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pccwg.test.api.exception.EmailAlreadyUsedException;
import org.pccwg.test.api.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ValidationExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<String> errors = new ArrayList<>();

    ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

    Map<String, List<String>> result = new HashMap<>();
    result.put("errors", errors);

    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<?> userNotFound(UserNotFoundException ex, HttpServletRequest request) {
	    List<String> errors = new ArrayList<>();
	    errors.add(ex.getMessage());
	    Map<String, List<String>> result = new HashMap<>();
	    result.put("errors", errors);
	    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(EmailAlreadyUsedException.class)
  public ResponseEntity<?> emailUsed(EmailAlreadyUsedException ex, HttpServletRequest request) {
	    List<String> errors = new ArrayList<>();
	    errors.add(ex.getMessage());
	    Map<String, List<String>> result = new HashMap<>();
	    result.put("errors", errors);
	    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }
}