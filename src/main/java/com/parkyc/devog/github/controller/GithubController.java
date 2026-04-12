package com.parkyc.devog.github.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    @GetMapping("/callback-github")
    public String successOAuthLogin(){
        return "Success Github OAuth Login";
    }
}


