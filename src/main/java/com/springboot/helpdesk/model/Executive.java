package com.springboot.helpdesk.model;

import com.springboot.helpdesk.enums.JobTitle;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Executive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)// Auto create a new table.
    private long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String contact;

    @Enumerated(value = EnumType.STRING)
    private JobTitle jobTitle;

    @OneToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;
}
