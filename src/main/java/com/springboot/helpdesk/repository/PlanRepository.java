package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan,Long> {

}
