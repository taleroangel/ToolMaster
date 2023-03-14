package edu.puj.toolmaster.users.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
public class User extends DomainEntity {
    @Id
    public Long id;
    public String name;
    public String lastName;
    public Date birthDate;
    @ManyToOne
    public City city;
    public Boolean active = Boolean.TRUE;

    public User overrideWith(User other) {
        var overridenUser = this;

        if (other.name != null) {
            overridenUser = overridenUser.withName(other.name);
        }
        if (other.lastName != null) {
            overridenUser = overridenUser.withLastName(other.lastName);
        }
        if (other.birthDate != null) {
            overridenUser = overridenUser.withBirthDate(other.birthDate);
        }
        if (other.city != null) {
            overridenUser = overridenUser.withCity(other.city);
        }
        if (other.active != null) {
            overridenUser = overridenUser.withActive(other.active);
        }

        return overridenUser.withId(this.id);
    }
}
