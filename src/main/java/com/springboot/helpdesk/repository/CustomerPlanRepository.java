package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.dto.response.CustomerPlanInfoDto;
import com.springboot.helpdesk.model.CustomerPlan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerPlanRepository extends JpaRepository<CustomerPlan,Long> {

    @Query("""
select new com.springboot.helpdesk.dto.response.CustomerPlanInfoDto(cp.id,
cp.activationDate,
cp.endDate,
cp.amountPaid,
c.name,
p.id,
p.planType
)
from CustomerPlan cp
JOIN cp.customer c
JOIN cp.plan p
JOIN c.user u
where u.username = ?1
""")
    List<CustomerPlanInfoDto> getAllPlansByCustomerUsername(String username, Pageable pageable);
}
