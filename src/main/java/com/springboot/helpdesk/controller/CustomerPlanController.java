package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.CustomerPlanDto;
import com.springboot.helpdesk.dto.response.CustomerPlanInfoDto;
import com.springboot.helpdesk.service.CustomerPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CustomerPlanController {

    /*
    API to add customer buys plan record in customer_plan table with all relevant fields
    * */
    private final CustomerPlanService customerPlanService;

    @PostMapping("/api/customerplan/add/{customerId}/{planId}")
    public void insertCustomerPlan(@PathVariable Long customerId,
                                   @PathVariable Long planId,
                                   @Valid @RequestBody CustomerPlanDto customerPlanDto){
        customerPlanService.insertCustomerPlan(customerId,planId,customerPlanDto);
    }

    /*
     * GET API : to fetch all plans based on customer username.
     * */
    @GetMapping("/api/customerplan/all")
    public List<CustomerPlanInfoDto> getAllPlansByCustomerUsername(@RequestParam String username,
                                                                       @RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
                                                                       @RequestParam(name = "size",required = false,defaultValue = "10") Integer size){
        return customerPlanService.getAllPlansByCustomerUsername(username,page,size);
    }

}
