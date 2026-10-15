package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.dto.response.TicketInfoDtoV2;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.dto.response.TicketStatusCustomerCountDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.model.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    // findBy -- Derived Query Methods
    // Id - findById


    List<Ticket> findByPriority(Priority priority); // select t from Ticket t where t.priority=?1
    List<Ticket> findByStatus(Status status); // select t from Ticket t where t.status=?1
    List<Ticket> findByPriorityAndStatus(Priority priority, Status status);
    // select t from Ticket t where t.priority=?1 and t.status=?2

    @Query("""
            select new com.springboot.helpdesk.dto.response.TicketInfoDto(t.id,t.subject,
            t.createdAt,t.priority,t.status,c.name,e.name,e.email)
            from Ticket t
            JOIN t.customer c
            JOIN t.executive e
            where c.id = ?1
            """)
    List<TicketInfoDto> getTicketsByCustomerId(Long customerId, Pageable pageable);

    @Query("""
            select new com.springboot.helpdesk.dto.response.TicketInfoDto(t.id,t.subject,
            t.createdAt,t.priority,t.status,c.name,e.name,e.email)
            from Ticket t
            JOIN t.customer c
            JOIN t.executive e
            JOIN c.user u
            where u.username = ?1
            """)
    List<TicketInfoDto> getTicketsByCustomerUsername(String customerUsername, Pageable pageable);

    @Query("""
            select new com.springboot.helpdesk.dto.response.TicketInfoDtoV2(
            t.id,
            e.id,
            e.name,
            t.subject,
            t.priority,
            t.status,
            t.createdAt,
            c.id,
            c.name
            )
            from Ticket t
            JOIN t.customer c
            JOIN t.executive e
            JOIN e.user u
            where u.username=?1
            """)
    List<TicketInfoDtoV2> getTicketByExecutiveUsername(String executiveUsername, Pageable pageable);

    @Query("""
select t.status as ticketStatus,count(c.id) as numberOfCustomer
from Ticket t
JOIN t.customer c
group by t.status
""")
    List<TicketStatusCustomerCountDto> getTicketStatWithCustomer();


    @Query("""
            select t.id as ticketId, t.subject as subject, t.createdAt as createdAt, t.priority as priority, t.status as status
            from Ticket t
            where (?1 IS NULL OR t.priority = ?1)
            AND  (?2 IS NULL OR t.status = ?2)
            AND (?3 IS NULL OR t.createdAt >= ?3)/*  The fromDate is instand type*/
            AND (?4 IS NULL OR CAST(t.createdAt as localdate ) <= ?4)/*  The toDate is casted to localdate type*/
            """)
    List<TicketRespDto> dynamicFilterTicket(Priority priority,
                                            Status status,
                                            Instant fromDate,
                                            LocalDate toDate);
}
