package com.springboot.helpdesk.dto.response;

import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;

import java.time.Instant;

public record TicketInfoDtoV2(
        Long ticketId,
        Long executiveId,
        String executiveName,
        String ticketSubject,
        Priority ticketPriority,
        Status ticketStatus,
        Instant ticketCreatedAt,
        Long customerId,
        String customerName
) {
}
