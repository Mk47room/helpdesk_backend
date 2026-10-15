package com.springboot.helpdesk.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;
    private String city;
    private String email;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @UpdateTimestamp
    private Instant updatedAt;
}
