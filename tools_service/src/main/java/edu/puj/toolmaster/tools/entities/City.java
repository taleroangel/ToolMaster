package edu.puj.toolmaster.tools.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A class that represents a city in which tools will be available
 */
@AllArgsConstructor
@NoArgsConstructor
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
