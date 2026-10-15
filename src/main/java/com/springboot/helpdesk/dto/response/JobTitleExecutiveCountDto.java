package com.springboot.helpdesk.dto.response;

import com.springboot.helpdesk.enums.JobTitle;

public record JobTitleExecutiveCountDto(
        JobTitle jobTitle,
        long numberOfExecutives
) {
}
