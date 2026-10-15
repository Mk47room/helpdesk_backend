package com.springboot.helpdesk.dto.response;

import com.springboot.helpdesk.enums.PlanType;

import java.time.LocalDate;

public record CustomerPlanInfoDto(
        Long CustomerPlanId,
        LocalDate activationDate,
        LocalDate endDate,
        double amountPaid,
        String customerName,
        Long PlanId,
        PlanType planType
) {
}
