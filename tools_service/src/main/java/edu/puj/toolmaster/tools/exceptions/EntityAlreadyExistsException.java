package edu.puj.toolmaster.tools.exceptions;

import edu.puj.toolmaster.tools.entities.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EntityAlreadyExistsException extends RuntimeException {
    DomainEntity resource;
}
