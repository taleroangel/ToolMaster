package edu.puj.toolmaster.tools.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

/**
 * Class that represents a tools brand
 */
@Entity
@Getter
@ToString
public class Brand extends DomainEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
