package com.parkyc.devog.github.service.command;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ContributesCommand(
        String loginId,
        LocalDate fromDate,
        LocalDate toDate
) { }
