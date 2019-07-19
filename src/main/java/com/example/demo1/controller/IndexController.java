package com.example.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @Controller：把当前的类作为路由api的承载着
 * */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
