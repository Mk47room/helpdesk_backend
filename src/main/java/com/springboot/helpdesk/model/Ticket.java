package com.springboot.helpdesk.model;

import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String subject;

    @Column(length = 1000)
    private String issue;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;
    @CreationTimestamp
    private Instant updatedAt;
    @Enumerated(value = EnumType.STRING)
    private Priority priority;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "executive_id")
    private Executive executive;

}
