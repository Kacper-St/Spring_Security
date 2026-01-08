package com.secure.spring_security.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hello Spring Security!";
    }

    @GetMapping("/hi")
    public String hello2() {
        return "Hello Spring Security!";
    }
}
