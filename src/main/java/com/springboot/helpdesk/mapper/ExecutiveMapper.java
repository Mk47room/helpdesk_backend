package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.ExecutiveDto;
import com.springboot.helpdesk.model.Executive;
import org.springframework.stereotype.Component;

@Component
public class ExecutiveMapper {

    public static Executive mapDtoToExecutive(ExecutiveDto executiveDto){
        Executive executive = new Executive();
        executive.setName(executiveDto.name());
        executive.setEmail(executiveDto.email());
        executive.setContact(executiveDto.contact());
        executive.setJobTitle(executiveDto.jobTitle());

        return executive;
    }
}
