package com.springboot.helpdesk.dto.request;

import com.springboot.helpdesk.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketDto(
        @NotNull(message = "Subject is required")
        @NotBlank(message = "Subject is required")
        String subject,
        @NotNull(message = "Issue is required")
        @NotBlank(message = "Issue is required")
        String issue,
        Priority priority
) {
}
