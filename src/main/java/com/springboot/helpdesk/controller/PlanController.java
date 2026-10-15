package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.model.Plan;
import com.springboot.helpdesk.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PlanController {

    private final PlanService planService;

    /*  POST API to insert simple plans in DB */
    @PostMapping("/api/plan/add")
    public void addPlan(@RequestBody Plan plan){
        planService.addPlan(plan);
    }
}
