package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long> {

    @Query("""
select m
from Manager m
JOIN m.user u
where u.username = ?1
""")
    Manager getByUsername(String managerUsername);
}
