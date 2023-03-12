package edu.puj.toolmaster.tools.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class that represents a Tool
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
@ToString
public class Tool extends DomainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Brand brand;

    @Column
    private String description;

    @Column
    private String image;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(cascade = CascadeType.ALL)
    private List<City> cities;

    @Column(nullable = false)
    private Integer units = 0;


    public Tool overrideWith(Tool other) {
        var overridenTool = this;

        if (other.name != null) {
            overridenTool = overridenTool.withName(other.name);
        }
        if (other.brand != null) {
            overridenTool = overridenTool.withBrand(other.brand);
        }
        if (other.description != null) {
            overridenTool = overridenTool.withDescription(other.description);
        }
        if (other.image != null) {
            overridenTool = overridenTool.withImage(other.image);
        }
        if (other.price != null) {
            overridenTool = overridenTool.withPrice(other.price);
        }
        if (other.cities != null) {
            overridenTool = overridenTool.withCities(other.cities);
        }
        if (other.units != null) {
            overridenTool = overridenTool.withUnits(other.units);
        }

        return overridenTool.withId(this.id);
    }
}
