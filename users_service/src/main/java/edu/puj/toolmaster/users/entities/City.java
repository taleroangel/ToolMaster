package edu.puj.toolmaster.users.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

/**
 * A class that represents a city in which tools will be available
 */
@Entity
@Getter
@ToString
public class City extends DomainEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
