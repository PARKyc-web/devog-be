package com.parkyc.devog.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {

    @GetMapping("/oauth")
    public String githubOAuthPage(){
        return "/github_oauth";
    }
}

