package com.springboot.helpdesk.dto.request;

public record CustomerUpdateDto(
        // @Pattern(regexp = "[a-zA-Z ]+")
        String name,
        String city
) {
}
