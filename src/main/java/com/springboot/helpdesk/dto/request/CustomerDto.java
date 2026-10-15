package com.springboot.helpdesk.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerDto(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        String city,
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        @Email(message = "Email seems invalid")
        String email,
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        @Size(min = 3,max = 15)
        String username,
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        String password

) {
}
