package edu.puj.toolmaster.tools.services.exceptions;

import edu.puj.toolmaster.tools.entities.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.text.html.parser.Entity;

@AllArgsConstructor
@Getter
public class EntityAlreadyExists extends RuntimeException {
    DomainEntity resource;
}
