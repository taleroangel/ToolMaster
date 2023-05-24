package edu.puj.toolmaster.tools.entities;


import jakarta.persistence.*;
import jakarta.persistence.metamodel.StaticMetamodel;
import lombok.*;

/**
 * Class that represents a tools brand
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@ToString
@StaticMetamodel(Brand.class)
public class Brand extends DomainEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
