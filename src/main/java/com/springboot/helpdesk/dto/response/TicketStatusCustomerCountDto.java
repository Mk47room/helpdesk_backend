package com.springboot.helpdesk.dto.response;

import com.springboot.helpdesk.enums.Status;

public record TicketStatusCustomerCountDto(
        Status ticketStatus,
        long numberOfCustomer
) {
}
