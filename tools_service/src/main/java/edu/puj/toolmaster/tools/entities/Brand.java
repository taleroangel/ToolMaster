package edu.puj.toolmaster.tools.entities;


import jakarta.persistence.*;
import lombok.Getter;

/**
 * Class that represents a tools brand
 */
@Entity
@Getter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
