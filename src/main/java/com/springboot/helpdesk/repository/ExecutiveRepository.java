package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.dto.response.JobTitleExecutiveCountDto;
import com.springboot.helpdesk.model.Executive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutiveRepository extends JpaRepository<Executive,Long> {

    @Query("""
select e.jobTitle as jobTitle,count(e.id) as numberOfExecutives
from Executive e
where e.jobTitle IS NOT null
group by e.jobTitle
""")
    List<JobTitleExecutiveCountDto> getExecutiveStatWithJobTitleAndCount();
}
