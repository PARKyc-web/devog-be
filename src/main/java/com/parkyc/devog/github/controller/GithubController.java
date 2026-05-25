package com.parkyc.devog.github.controller;

import com.parkyc.devog.security.DevogPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GithubController {

    public String getContribute(@AuthenticationPrincipal DevogPrincipal user){



        return "";
    }
}
