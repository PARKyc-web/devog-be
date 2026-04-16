package com.parkyc.devog.activity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    public String loadHeatmap(){

        return "heatmap-data";
    }

}
