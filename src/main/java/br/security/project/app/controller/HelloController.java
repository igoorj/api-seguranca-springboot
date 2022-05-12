package br.security.project.app.controller;

import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return "Hi, i am authenticate to delete";
    }
}
