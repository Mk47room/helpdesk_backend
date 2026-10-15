package com.springboot.helpdesk.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ManagerDto(
        @NotBlank(message = "Name is required")
        @NotNull(message = "Name is required")
        String name,
        @NotBlank(message = "Username is required")
        @NotNull(message = "Username is required")
        String username,
        @NotBlank(message = "password is required")
        @NotNull(message = "password is required")
        String password
) {
}
