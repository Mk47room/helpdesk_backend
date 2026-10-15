package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.CustomerPlanDto;
import com.springboot.helpdesk.model.CustomerPlan;
import org.springframework.stereotype.Component;

@Component
public class CustomerPlanMapper {

    public static CustomerPlan mapDtoToCustomerPlan(CustomerPlanDto dto){
        CustomerPlan customerPlan = new CustomerPlan();
        customerPlan.setActivationDate(dto.activationDate());
        customerPlan.setEndDate(dto.endDate());
        customerPlan.setCoupon(dto.coupon());
        customerPlan.setAmountPaid(dto.amountPaid());
        return customerPlan;
    }
}
