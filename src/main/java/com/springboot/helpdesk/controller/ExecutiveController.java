package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.ExecutiveDto;
import com.springboot.helpdesk.dto.response.TicketInfoDtoV2;
import com.springboot.helpdesk.service.ExecutiveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ExecutiveController {

    public final ExecutiveService executiveService;

         /*
     DTO (name,email,contact)
     validation
     Map DTO to Entity
     Insert Entity IN Db
     * */
    @PostMapping("/api/executive/add")
    public void insertExecutive(@Valid @RequestBody ExecutiveDto executiveDto,
                                Principal principal){
        executiveService.add(executiveDto,principal.getName());
    }
}
