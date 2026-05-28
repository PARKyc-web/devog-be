package com.parkyc.devog.activity.controller;

import com.parkyc.devog.activity.controller.request.ActivitySearchRequest;
import com.parkyc.devog.activity.service.ActivityService;
import com.parkyc.devog.common.response.ApiResponse;
import com.parkyc.devog.security.DevogPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/list")
    public ApiResponse<String> totalActivity(ActivitySearchRequest search,
                                             @AuthenticationPrincipal DevogPrincipal user){



        return ApiResponse.ok("ACTIVITY - RESPONSE");
    }
}
