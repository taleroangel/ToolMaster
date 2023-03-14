package edu.puj.toolmaster.users.controller;

import edu.puj.toolmaster.users.entities.User;
import edu.puj.toolmaster.users.services.UserService;
import edu.puj.toolmaster.users.services.exceptions.EntityAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    Page<User> getAllUsers(@RequestParam(defaultValue = "0") Integer page,
                           @RequestParam(defaultValue = "10") Integer size,
                           @RequestParam(defaultValue = "name") String sort) {
        return service.getAllUsers(PageRequest.of(page, size, Sort.by(sort)));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        try {
            return new ResponseEntity<User>(service.addNewUser(user), HttpStatus.CREATED);
        } catch (EntityAlreadyExistsException e) {
            // Use Liskov Substitution principle to return a user from a DomainEntity
            return new ResponseEntity<User>((User) e.getResource(), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUserById(@PathVariable Long id) {
        service.deleteUserById(id);
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User updateUserById(@PathVariable Long id, @RequestBody User user) {
        return service.updateUserById(id, user);
    }

    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User partialUserUpdate(@PathVariable Long id, @RequestBody User user) {
        return service.partialUserUpdateById(id, user);
    }
}
