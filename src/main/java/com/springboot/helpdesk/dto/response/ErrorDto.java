package com.springboot.helpdesk.dto.response;

import java.time.Instant;

public record ErrorDto(
        String message,
        String comment,
        Instant timestamp
) {
}
