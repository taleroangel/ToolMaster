package edu.puj.toolmaster.tools.entities;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * A class that represents a city in which tools will be available
 */
@Entity
@Getter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
