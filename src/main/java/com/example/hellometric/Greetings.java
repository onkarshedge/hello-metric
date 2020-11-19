package com.example.hellometric;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class Greetings {

    @GetMapping
    public String hello() {
        return "hola world !!";
    }
}
