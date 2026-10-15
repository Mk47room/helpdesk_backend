package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.dto.response.TicketInfoDtoV2;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.dto.response.TicketStatusCustomerCountDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.model.Ticket;
import com.springboot.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TickerController {

    private final TicketService ticketService;
    @PostMapping("/api/ticket/add")
    public void postTicket(Principal principal,
                           @Valid @RequestBody TicketDto ticketDto){
        ticketService.add(principal.getName(),ticketDto);
    }

    // Done by Manager - activate the check after security config
    @PutMapping("/api/ticket/assign/{executiveId}/{ticketId}")
    public void assignExecutive(@PathVariable Long executiveId,
                                @PathVariable Long ticketId,
                                Principal principal){
        ticketService.assignExecutive(executiveId,ticketId,principal.getName());
    }

    @GetMapping("/api/ticket/v1/{customerId}")
    public List<TicketInfoDto> getTicketsByCustomerId(@PathVariable Long customerId,
                                                      @RequestParam("page") int page,
                                                      @RequestParam("size") int size) {
        return ticketService.getTicketsByCustomerId(customerId, page, size);
    }


    @GetMapping("/api/ticket/v2")
    public List<TicketInfoDto> getTicketsByCustomerUsername(@RequestParam("username") String customerUsername,
                                             @RequestParam(name = "page",required = false,defaultValue = "0") Integer page ,
                                             @RequestParam(name = "size",required = false,defaultValue = "5") Integer size ){
        return ticketService.getTicketsByCustomerUsername(customerUsername,page,size);
    }

    @GetMapping("/api/ticket/executive")
    public List<TicketInfoDtoV2> getTicketByExecutiveUsername(
            @RequestParam("username") String executiveUsername,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size)
    {
        return ticketService.getTicketByExecutiveUsername(executiveUsername, page, size);
    }

    @GetMapping("/api/ticket/status/count/customer")
    public List<TicketStatusCustomerCountDto> getTicketStatWithCustomer(){
        return ticketService.getTicketStatWithCustomer();
    }

    @GetMapping("/api/ticket/by-priority")
    public List<TicketRespDto>getTicketsByPriority(@RequestParam("priority") Priority priority){
        return ticketService.getTicketsByPriority(priority);
    }

    @GetMapping("/api/ticket/by-status")
    public List<TicketRespDto>getTicketsByStatus(@RequestParam("status") Status status){
        return ticketService.getTicketsByStatus(status);
    }

    @GetMapping("/api/ticket/by-priority-status")
    public List<TicketRespDto>getTicketsByPriorityAndStatus(@RequestParam("priority") Priority priority,
                                                            @RequestParam("status") Status status){
        return ticketService.getTicketsByPriorityAndStatus(priority,status);
    }

    @GetMapping("/api/ticket/filter")
    public List<TicketRespDto> dynamicFilterTicket(@RequestParam(value = "priority", required = false) Priority priority,
                                                   @RequestParam(value = "status", required = false) Status status,
                                                   @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
                                                   @RequestParam(value = "toDate", required = false) LocalDate toDate){
        return ticketService.dynamicFilterTicket(priority,status,fromDate,toDate);
    }

}
/*
    OPEN tickets
    ------------
    ticket 1 - id [Assign] -- select executive [executiveId] :- ticketId, executiveId
    ticket 2 - id [Assign]
 */