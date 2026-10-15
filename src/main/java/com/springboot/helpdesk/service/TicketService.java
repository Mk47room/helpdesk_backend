package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.dto.response.TicketInfoDtoV2;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.dto.response.TicketStatusCustomerCountDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.exception.OwnershipException;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.mapper.TicketMapper;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.Executive;
import com.springboot.helpdesk.model.Ticket;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.ExecutiveRepository;
import com.springboot.helpdesk.repository.TicketRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final ExecutiveRepository executiveRepository;

    public void add( String customerUserName, @Valid TicketDto ticketDto) {
        // Step 1: Fetch Customer Object from DB using given customerId
        Customer customer = customerRepository.getByUserName(customerUserName);

        /*
void m1(){
    if(this == true)
        throw new ResourceNotFoundException("Customer id Invalid");
}

()->new ResourceNotFoundException("Customer id Invalid")
()->{
    if(this == true)
        throw new ResourceNotFoundException("Customer id Invalid");
}
* */

        // Step 2: Map TicketDto to Ticket entity
        Ticket ticket = TicketMapper.convertDtoToTicket(ticketDto);

        // Step 3: Attach customer to Ticket
        ticket.setCustomer(customer);

        // Step 3.5: Assign Status as Open to Ticket
        ticket.setStatus(Status.OPEN);
        // Step 4: Insert Ticket in DB
        ticketRepository.save(ticket);
    }

    public void assignExecutive(Long executiveId, Long ticketId, String managerUsername) {
        //Step 1: Fetch Executive obj from DB using Id.
        Executive executive = executiveRepository.findById(executiveId)
                .orElseThrow(()-> new ResourceNotFoundException("Executive not found"));
        // Step 1.5 Does this executive belong to the team of logged-in manager?
        if(! (executive.getManager().getUser().getUsername().equals(managerUsername))){
            throw new OwnershipException("Operation not allowed! contact admin");
        }


        //Step 2: Fetch Ticket obj from DB using Id.
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(()-> new ResourceNotFoundException("Ticket not found"));

        // Step 3: Attach executive to ticket
        ticket.setExecutive(executive);

        //Step 4: Save it in DB
        ticketRepository.save(ticket);
    }

    public List<TicketInfoDto> getTicketsByCustomerId(Long customerId, int page, int size) {
        // Validate customerId
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Id Invalid"));

        // Create an Instance of Pageable interface using page and size values thru PageRequest
        Pageable pageable = PageRequest.of(page,size);
        return ticketRepository.getTicketsByCustomerId(customerId,pageable);
    }

    public List<TicketInfoDto> getTicketsByCustomerUsername(String customerUsername, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return ticketRepository.getTicketsByCustomerUsername(customerUsername,pageable);
    }

    public List<TicketInfoDtoV2> getTicketByExecutiveUsername(String executiveUsername, Integer page, Integer size) {
        Pageable pageable =  PageRequest.of(page,size);
        return ticketRepository.getTicketByExecutiveUsername(executiveUsername, pageable);
    }

    public List<TicketStatusCustomerCountDto> getTicketStatWithCustomer() {
        return ticketRepository.getTicketStatWithCustomer();
    }

    public List<TicketRespDto> getTicketsByPriority(Priority priority) {
        List<Ticket> list = ticketRepository.findByPriority(priority);
        return  list
                .stream()
                .map(TicketMapper :: convertTicketToDto)
                .toList();
    }

    public List<TicketRespDto> getTicketsByStatus(Status status) {
        List<Ticket> list = ticketRepository.findByStatus(status);
        return  list
                .stream()
                .map(TicketMapper :: convertTicketToDto)
                .toList();
    }

    public List<TicketRespDto> getTicketsByPriorityAndStatus(Priority priority, Status status) {
        List<Ticket> list = ticketRepository.findByPriorityAndStatus(priority,status);
        return  list
                .stream()
                .map(TicketMapper :: convertTicketToDto)
                .toList();
    }

    public List<TicketRespDto> dynamicFilterTicket(Priority priority,
                                                   Status status,
                                                   LocalDate fromDate,
                                                   LocalDate toDate) {

        return ticketRepository.dynamicFilterTicket(priority,
                status,
                //cast LocalDate from API to Instant (db has Instant)to check in db, so the date has time now (00:00)
                fromDate == null ? null: fromDate.atStartOfDay(ZoneOffset.UTC).toInstant(),
                //Instead cast here we cast while fetching..
                toDate);
    }
} // yyyy-MM-ddThh:mm:sec.nano
