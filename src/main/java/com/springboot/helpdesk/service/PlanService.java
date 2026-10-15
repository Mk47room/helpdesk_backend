package com.springboot.helpdesk.service;

import com.springboot.helpdesk.model.Plan;
import com.springboot.helpdesk.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlanRepository planRepository;

    public void addPlan(Plan plan) {
        planRepository.save(plan);
    }
}
