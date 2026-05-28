package com.parkyc.devog.activity.service.result;

import java.util.List;

public record ActivitySummary(
        Long totalCount,
        List<ActivityDayCount> activities
) {
}
