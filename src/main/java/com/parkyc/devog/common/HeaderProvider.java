package com.parkyc.devog.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class HeaderProvider {

    public String isPlatform(HttpServletRequest request){
        String platform = request.getHeader("devog-platform");
        if(platform == null || platform.isBlank()){
            return "unknown";
        }

        return "web".equals(platform) ? "web" : "app";
    }

    public boolean isWebPlatform(HttpServletRequest request){
        return isPlatform(request).equals("web");
    }
    public boolean isAppPlatform(HttpServletRequest request){
        return !isPlatform(request).equals("web");
    }
}
