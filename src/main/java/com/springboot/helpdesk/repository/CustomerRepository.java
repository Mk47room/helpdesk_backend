package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("""
select c
from Customer c
JOIN c.user u
where u.username = ?1
""")
    Customer getByUserName(String customerUserName);
    /*
    JpaRepository <Interface>
    save(T) : T
    findAll() : List<T>
    findById(id) : Optional<T>
    delete(T) : void
    deleteById(id) : void
    * */


}

// JpaRepository : spring data jpa

// class CustomerRepository implements JpaRepository : implement all these methods
// interface CustomerRepository extends JpaRepository : i get all pre created methods