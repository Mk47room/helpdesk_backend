package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.CustomerPlanDto;
import com.springboot.helpdesk.dto.response.CustomerPlanInfoDto;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.mapper.CustomerPlanMapper;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.CustomerPlan;
import com.springboot.helpdesk.model.Plan;
import com.springboot.helpdesk.repository.CustomerPlanRepository;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerPlanService {
    private final CustomerPlanRepository customerPlanRepository;
    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;

    public void insertCustomerPlan(Long customerId,Long planId,CustomerPlanDto customerPlanDto) {
        //Step 1: validate customer and plan
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("Customer id Invalid"));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(()-> new ResourceNotFoundException("Plan id Invalid"));

        //Step 2 : Map dto to CustomerPlan
        CustomerPlan customerPlan = CustomerPlanMapper.mapDtoToCustomerPlan(customerPlanDto);

        //Step 3 : Attach customer and plan to Entity
        customerPlan.setCustomer(customer);
        customerPlan.setPlan(plan);

        //Step 4: insert into DB
        customerPlanRepository.save(customerPlan);
    }

    public List<CustomerPlanInfoDto> getAllPlansByCustomerUsername(String username, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return customerPlanRepository.getAllPlansByCustomerUsername(username,pageable);
    }
}
