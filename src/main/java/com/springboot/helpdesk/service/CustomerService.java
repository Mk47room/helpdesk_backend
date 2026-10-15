package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.CustomerDto;
import com.springboot.helpdesk.dto.request.CustomerUpdateDto;
import com.springboot.helpdesk.enums.Role;
import com.springboot.helpdesk.exception.InvalidCallException;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.mapper.CustomerMapper;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.User;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void insertCustomer(CustomerDto customerDto) {
        // Step 1: Prepare user obj and save it in DB
        User user = new User();
        user.setUsername(customerDto.username());
        user.setPassword(customerDto.password());
        user.setRole(Role.CUSTOMER);
        user = userRepository.save(user);


        // Step 1.5 : Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Map CustomerDto to Customer entity
        Customer customer = CustomerMapper.mapDtoToCustomer(customerDto);
        customer.setUser(user);

        // Save Customer entity
        customerRepository.save(customer);
    }

    public Customer getById(long id) {
        Optional<Customer> optional = customerRepository.findById(id);
        if(optional.isEmpty())
            throw new ResourceNotFoundException("Customer Id Invalid");
        return optional.get();
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public void deleteById(long id) {
        // Check if id is valid
        getById(id);
        customerRepository.deleteById(id);
    }

    public void update(Long id, CustomerUpdateDto dto) {
        Customer customer = getById(id);
        boolean flag = false;

        if(!(dto.name()  == null) && !(dto.name().trim().isEmpty())) {    // If dto.name() is NOT(!) empty
            customer.setName(dto.name());
            flag = true;
        }

        if(!(dto.city() == null) && !(dto.city().trim().isEmpty())) {
            customer.setCity(dto.city());
            flag = true;
        }

        if(!flag)
            throw new InvalidCallException("Update op terminated");

        customerRepository.save(customer);

    }
}
