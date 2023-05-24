package edu.puj.toolmaster.users.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.metamodel.StaticMetamodel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Date;

/**
 * Entidad para representar un usuario
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
@StaticMetamodel(User.class)
public class User extends DomainEntity {
    @Id
    public Long id;
    public String name;
    public String lastName;
    public Date birthDate;
    @ManyToOne
    public City city;
    public Boolean active = Boolean.TRUE;

    /**
     * Construir el nombre de usuario a partir del nombre y apellido
     * @return Nombre de usuario
     */
    public String getUsername() {
        return name.toLowerCase() + "_" + lastName.toLowerCase();
    }

    /**
     * Sobre los campos nulos del usuario actual con los campos no nulos del otro usuario
     * @param other Usuario con campos no nulos para sobreescribir
     * @return Usuario sobreescrito
     */
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
