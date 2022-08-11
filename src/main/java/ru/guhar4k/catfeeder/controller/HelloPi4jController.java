package ru.guhar4k.catfeeder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloPi4jController {

    @GetMapping
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("Hello there!", HttpStatus.OK);
    }
}
