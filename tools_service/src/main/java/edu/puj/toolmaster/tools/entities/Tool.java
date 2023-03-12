package edu.puj.toolmaster.tools.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Class that represents a Tool
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Brand brand;

    @Column
    private String description;

    @Column(nullable = false)
    private Double price;

    @OneToMany
    @Column(nullable = false)
    private List<City> cities;

    @Column(nullable = false)
    private Integer units = 0;
}
