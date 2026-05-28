package com.parkyc.devog.activity.service.result;

import java.time.LocalDate;

public record ActivityDayCount(
        LocalDate actionTime,
        Long count
){}
