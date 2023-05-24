package edu.puj.toolmaster.users.controller;

import edu.puj.toolmaster.users.entities.User;
import edu.puj.toolmaster.users.services.UserService;
import edu.puj.toolmaster.users.exceptions.EntityAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de usuarios
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    UserService service;

    /**
     * Obtener todos los usuarios
     * @param page Número de la página
     * @param size Tamaño de la página
     * @param sort Criterio de ordenamiento
     * @return Respuesta json
     */
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "10") Integer size,
                                  @RequestParam(defaultValue = "name") String sort) {
        return service.getAllUsers(PageRequest.of(page, size, Sort.by(sort)));
    }

    /**
     * Obtener todos los usuarios por su nombre
     * @param page Número de la página
     * @param size Tamaño de la página
     * @param sort Criterio de ordenamiento
     * @param name Nombre del usuario
     * @return Respuesta json
     */
    @GetMapping(value = "/search/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Page<User> searchUsersByName(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        @RequestParam(defaultValue = "name") String sort,
                                        @PathVariable String name) {
        return service.userByNameLike(name, PageRequest.of(page, size, Sort.by(sort)));
    }

    /**
     * Obtener un usuario por su ID
     * @param id Id del usuario
     * @return respuesta en JSON
     */
    @GetMapping(value = "/id/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    /**
     * Crear un nuevo usuario
     * @param user Usuario nuevo
     * @return Respuesta en JSON
     */
    @PostMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(service.addNewUser(user), HttpStatus.CREATED);
        } catch (EntityAlreadyExistsException e) {
            // Use Liskov Substitution principle to return a user from a DomainEntity
            return new ResponseEntity<>((User) e.getResource(), HttpStatus.OK);
        }
    }

    /**
     * Borrar un usuario por su ID
     * @param id ID del usuario a borrar
     */
    @DeleteMapping(value = "/{id}")
    public void deleteUserById(@PathVariable Long id) {
        service.deleteUserById(id);
    }

    /**
     * Actualizar un usuario por su ID
     * @param id ID del usuario
     * @param user usuario
     * @return JSON con la respuesta
     */
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User updateUserById(@PathVariable Long id, @RequestBody User user) {
        return service.updateUserById(id, user);
    }

    /**
     * Actualizar parcialmente un usuario por su id
     * @param id ID del usuario
     * @param user usuario
     * @return JSON con la respuesta
     */
    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User partialUserUpdate(@PathVariable Long id, @RequestBody User user) {
        return service.partialUserUpdateById(id, user);
    }
}
