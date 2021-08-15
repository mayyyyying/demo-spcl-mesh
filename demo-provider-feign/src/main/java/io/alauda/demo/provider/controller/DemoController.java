package io.alauda.demo.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demo")
@RestController
public class DemoController {

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/feign/ping")
    public String hello(){
        return appName;
    }

    @PostMapping("/feign/ping")
    public String postHello(){
        return appName;
    }

    @GetMapping("/feign/error")
    public String error() throws Exception {
        throw new Exception("error!");
    }
}
