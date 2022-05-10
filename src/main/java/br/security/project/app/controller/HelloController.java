package br.security.project.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hi, i am authenticate!";
    }

    @GetMapping("/{id}")
    public String hello2(@PathVariable String id) {
        return "Hi, i am authenticate with id: " + id;
    }
}
