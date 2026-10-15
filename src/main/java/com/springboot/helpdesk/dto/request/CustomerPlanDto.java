package com.springboot.helpdesk.dto.request;

import com.springboot.helpdesk.enums.PlanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CustomerPlanDto(
        LocalDate activationDate,
        LocalDate endDate,
        String coupon,
        double amountPaid
) {
}
