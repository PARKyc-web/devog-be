package com.parkyc.devog.activity.service.command;

import com.parkyc.devog.security.DevogPrincipal;

import java.time.LocalDate;

public record ActivityCommand(
        Long memberId,
        LocalDate fromDate,
        LocalDate toDate
) { }
