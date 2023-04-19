package edu.puj.toolmaster.users.entities;

import jakarta.persistence.*;
import jakarta.persistence.metamodel.StaticMetamodel;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
@StaticMetamodel(City.class)
public class City extends DomainEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
