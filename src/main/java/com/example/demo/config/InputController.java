package com.example.demo.config;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputController {
  /* http://localhost:8080/hello */
  /* http://localhost:8080/hello?name=Gudrun */
  @GetMapping("/hello")
  public ResponseEntity<?> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new ResponseEntity<>(String.format("Welcome %s!", name), HttpStatusCode.valueOf(200));
  }
}
