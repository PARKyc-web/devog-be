package com.parkyc.devog.activity.controller.request;

import com.parkyc.devog.activity.service.command.ActivityCommand;
import com.parkyc.devog.security.DevogPrincipal;

import java.time.LocalDate;

// Think : 시간을 받야야 함. 그냥 LocalDate로 받는게 좋을지, 아니면 Year, Month 이런식으로 받으면 좋을지
public record ActivitySearchRequest(
        LocalDate fromDate,
        LocalDate toDate
) {

    // Think : 만약 값이 없을 경우 최근 2달로 고정해서 검색한다.
    public ActivitySearchRequest {
        LocalDate now = LocalDate.now();
        if(fromDate == null){
            fromDate = now.minusMonths(2);
        }
        if(toDate == null){
            toDate = now;
        }

        // 만약 fromDate가 toDate보다 크다면
        if(fromDate.isAfter(toDate)){
            fromDate = now.minusMonths(2);
            toDate = now;
        }
    }

    public ActivityCommand toCommand(DevogPrincipal user){
        return new ActivityCommand(user.memberId(), this.fromDate, this.toDate);
    }
}

/* 검색조건이 뭐뭐 필요한지? 그냥 몇월에을 조회할것인지에 대한 정보만 있으면 되나? */
