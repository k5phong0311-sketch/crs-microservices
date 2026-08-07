package com.example.courseservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Chào mừng bạn đến với Course Service API! Vui lòng thêm '/courses' vào sau đường dẫn để sử dụng API. (Ví dụ: http://localhost:8082/courses)";
    }
}
