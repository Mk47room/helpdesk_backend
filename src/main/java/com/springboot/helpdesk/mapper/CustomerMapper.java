package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.CustomerDto;
import com.springboot.helpdesk.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public static Customer mapDtoToCustomer(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setName(customerDto.name());
        customer.setCity(customerDto.city());
        customer.setEmail(customerDto.email());
        return customer;
    }
}
