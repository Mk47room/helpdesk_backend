package com.springboot.helpdesk.service;

import static org.junit.jupiter.api.Assertions.*;

import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.Ticket;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Test
    public void getTicketsByCustomerIdTest(){
        //Samples
        TicketInfoDto dto1 = new TicketInfoDto(1L,"test1", Instant.now(), Priority.LOW, Status.OPEN,"test_name","executive_name","testmail@gmail.com");
        TicketInfoDto dto2 = new TicketInfoDto(2L,"test2", Instant.now(), Priority.HIGH, Status.OPEN,"test_name","executive_name","testmail@gmail.com");
        TicketInfoDto dto3 = new TicketInfoDto(3L,"test3", Instant.now(), Priority.HIGH, Status.OPEN,"test_name","executive_name","testmail@gmail.com");
        Customer customer = new Customer();
        customer.setId(1L);

        Customer customer2 = new Customer();
        customer.setId(2L);

        //Assigning..
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));


        when(ticketRepository.getTicketsByCustomerId(1L,PageRequest.of(0,5)))
                .thenReturn(List.of(dto1,dto2));
        when(ticketRepository.getTicketsByCustomerId(2L,PageRequest.of(0,5)))
                .thenReturn(List.of(dto3));

        assertEquals(2,ticketService.getTicketsByCustomerId(1L,0,5).size());
        assertEquals(1,ticketService.getTicketsByCustomerId(2L,0,5).size());

        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).findById(2L);

        verify(ticketRepository, times(1))
                .getTicketsByCustomerId(1L,PageRequest.of(0,5));
        verify(ticketRepository, times(1))
                .getTicketsByCustomerId(2L,PageRequest.of(0,5));

        verifyNoMoreInteractions(customerRepository);
        verifyNoMoreInteractions(ticketRepository);
    }

    @Test
    public void getTicketsByCustomerIdTest_Invalid() {
        when(customerRepository.findById(5L)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class,
                ()-> ticketService.getTicketsByCustomerId(5L, 0, 5));

        verify(customerRepository, times(1)).findById(5L);

        verify(ticketRepository, times(0))
                .getTicketsByCustomerId(1L, PageRequest.of(0, 5));

        verifyNoMoreInteractions(customerRepository);
        verifyNoMoreInteractions(ticketRepository);
    }

    @Test
    public void saveTest(){
        /*
         * To call add method of service i need 2 things
         * 1. customerUsername
         * 2. ticketDto
         * this info comes from postman/react app via controller.
         * here , we need to define it ourself
         *
         * */
        // mock
        String customerUserName = "john";
        TicketDto ticketDto = new TicketDto("test_subject","test_issue",Priority.HIGH);

        // Before making actual call, lets mock the repositories in that add method
        Customer customer = new Customer();
        when(customerRepository.getByUserName(customerUserName)).thenReturn(customer);

        // when this save method is call, don't go to DB and save, just assume it saved and give back any ticket class
        when(ticketRepository.save(any(Ticket.class))).thenReturn(any(Ticket.class));

        //Actual call
        ticketService.add(customerUserName,ticketDto);

        // Tip: once u mock - it won't go to db but it still make a call.
        // verify if repo calls are happening only once
        verify(customerRepository,times(1)).getByUserName(customerUserName);
        verify(ticketRepository,times(1)).save(any(Ticket.class));
    }
}
