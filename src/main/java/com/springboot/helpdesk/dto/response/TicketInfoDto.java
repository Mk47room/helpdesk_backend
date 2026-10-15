package com.springboot.helpdesk.dto.response;

import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;

import java.time.Instant;

public record TicketInfoDto(
        Long ticketId,
        String subject,
        Instant createdAt,
        Priority priority,
        Status status,
        String customerName,
        String executiveName,
        String executiveEmail
) {
}
