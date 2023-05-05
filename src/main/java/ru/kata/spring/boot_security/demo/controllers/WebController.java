package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WebController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/user")
    public String user() {
        return "user";
    }

}
