package com.ScreeningHelper.Beyond.ScreeningHelper.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")        // 맨 처음 Default 도메인에서 호출될 함
    public String home() {
        return "home";
    }
}
