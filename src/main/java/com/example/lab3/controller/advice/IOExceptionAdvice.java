package com.example.lab3.controller.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class IOExceptionAdvice {

  @ExceptionHandler(IOException.class)
  public String handle(Model model, IOException exception) {
    model.addAttribute("error", exception.getMessage());
    return "tree";
  }
}
