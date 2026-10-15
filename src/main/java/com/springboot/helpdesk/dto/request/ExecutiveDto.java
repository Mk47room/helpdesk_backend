package com.springboot.helpdesk.dto.request;

import com.springboot.helpdesk.enums.JobTitle;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExecutiveDto(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "email is required")
        @NotBlank(message = "email is required")
        @Email(message = "Email seems invalid")
        String email,
        @NotNull(message = "contact is required")
        @NotBlank(message = "contact is required")
        @Size(min = 10,max = 10,message = "contact should be 10 digit mobile number" )
        String contact,
        @NotBlank
        @NotNull
        @Size(min = 3,max = 15)
        String username,
        @NotNull
        @NotBlank
        String password,
        JobTitle jobTitle

) {
}
