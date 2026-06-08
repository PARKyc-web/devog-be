package com.parkyc.devog.github.service.command;

import java.time.LocalDate;
import java.time.YearMonth;

public record ContributesCommand(
        String loginId,
        YearMonth yearMonth
) { }
