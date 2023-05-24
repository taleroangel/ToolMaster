package edu.puj.toolmaster.users.services;

import edu.puj.toolmaster.users.entities.City;
import edu.puj.toolmaster.users.entities.User;
import edu.puj.toolmaster.users.entities.User_;
import edu.puj.toolmaster.users.entities.auth.Auth;
import edu.puj.toolmaster.users.persistance.AuthRepository;
import edu.puj.toolmaster.users.persistance.CityRepository;
import edu.puj.toolmaster.users.persistance.UserRepository;
import edu.puj.toolmaster.users.exceptions.EntityAlreadyExistsException;
import edu.puj.toolmaster.users.exceptions.ResourceBadRequestException;
import edu.puj.toolmaster.users.exceptions.ResourceNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio encargado de obtener los recursos del repositorio de usuarios
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AuthRepository authRepository;

    /**
     * Obtener todos los usuarios
     *
     * @param pageable Paginación
     * @return Página de usuarios
     */
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAllByActiveTrue(pageable);
    }

    /**
     * Buscar usuarios con nombre que coincidan
     *
     * @param name     Nombre a buscar
     * @param pageable Paginación
     * @return Página de usuarios
     */
    public Page<User> userByNameLike(String name, Pageable pageable) {
        String matchName = "%" + name + "%";
        Specification<User> spec = Specification.where((root, query, cb) -> cb.like(root.get(User_.name), matchName));
        return userRepository.findAll(spec, pageable);
    }

    /**
     * Obtener usuarios por su ID
     *
     * @param id ID del usuario
     * @return Usuario encontrado
     * @throws ResourceNotFoundException El usuario no fue encontrado
     */
    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Obtener información completa de los usuarios
     *
     * @param user Usuario a completar
     * @return Usuario completo
     * @throws ResourceBadRequestException El usuario no tiene la información necesaria
     */
    public User parseUser(User user) throws ResourceBadRequestException {
        return user.withCity(parseUserCity(user));
    }

    /**
     * Obtener la ciudad de un usuario completa
     *
     * @param user Usuario a completar
     * @return Usuario con datos de la ciudad completos
     * @throws ResourceBadRequestException No tenía ciudad
     */
    public City parseUserCity(User user) throws ResourceBadRequestException {
        return user.getCity().getId() == null ? cityRepository.findByName(user.getCity().getName())
                                                        .orElseThrow(ResourceNotFoundException::new)
                       : cityRepository.findById(user.getCity().getId()).orElseGet(() -> cityRepository.save(user.getCity()));
    }

    /**
     * Agregar un nuevo servicio
     *
     * @param user Usuario a ser agregado
     * @return Usuario agregado
     * @throws ResourceBadRequestException  El usuario está en un formato incorrecto
     * @throws EntityAlreadyExistsException El usuario ya existía
     */
    public User addNewUser(@NonNull User user) throws ResourceBadRequestException, EntityAlreadyExistsException {
        try {
            // Get the parsed user
            User fixedUser = parseUser(user);

            // Find if user already exists
            Optional<User> findUser = userRepository.findOne(
                    Example.of(fixedUser, ExampleMatcher.matching()
                                                  .withIgnorePaths("id")));

            if (findUser.isPresent()) {
                throw new EntityAlreadyExistsException(findUser.get());
            }

            // Store it
            User ruser = userRepository.save(fixedUser);
            authRepository.save(new Auth(ruser.getId(), ruser.getUsername(), ruser.getUsername()));
            return ruser;

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            // Throws null pointer exception if brand or city could not be created
            throw new ResourceBadRequestException();
        }
    }

    /**
     * Borrar un usuario dado su id
     *
     * @param id ID
     * @throws ResourceNotFoundException el ID especificado no existe
     */
    public void deleteUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        // Logical Delete
        user = user.withActive(Boolean.FALSE);
        authRepository.deleteById(user.getId());
        userRepository.save(user);
    }

    /**
     * Actualizar un usuario dado su ID
     *
     * @param id   ID del usuario a actualizar
     * @param user Usuario con datos actualizados
     * @return Usuario con datos actualizados
     * @throws ResourceBadRequestException El usuario estaba en un formato incorrecto
     * @throws ResourceNotFoundException   El usuario no existía
     */
    public User updateUserById(Long id, User user) throws ResourceBadRequestException, ResourceNotFoundException {
        try {
            // Find already existing user
            User findUser = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
            // Parse the new user
            User parsedUser = parseUser(user);
            // Save the changes (Create auth if it was deactivated)
            User ruser = userRepository.save(parsedUser.withId(findUser.getId()));
            if (!findUser.getActive() && parsedUser.getActive()) {
                authRepository.save(new Auth(findUser.getId(), parsedUser.getUsername(), parsedUser.getUsername()));
            }
            return ruser;
        } catch (Exception e) {
            // Throws null pointer exception if brand or city could not be created
            throw new ResourceBadRequestException();
        }
    }

    /**
     * Actualizar parcialmente un usuario (ignorar campos nulos)
     *
     * @param id   ID del usuario a actualizar
     * @param user Usuario con información a actualizar (campos nulos permitidos)
     * @return Usuario con la información completa
     * @throws ResourceBadRequestException El usuario estaba en formato incorrecto
     */
    public User partialUserUpdateById(Long id, User user) throws ResourceBadRequestException {
        try {
            // Find already existing user
            User findUser = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
            // Build new user overriding old fields
            User overrideUser = findUser.overrideWith(user);
            // Parse the new user
            if (user.getCity() != null) {
                overrideUser = overrideUser.withCity(parseUserCity(overrideUser));
            }
            User ruser = userRepository.save(overrideUser);
            if (!findUser.getActive() && overrideUser.getActive()) {
                authRepository.save(new Auth(findUser.getId(), overrideUser.getUsername(), overrideUser.getUsername()));
            }
            // Save the changes
            return ruser;
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            // Throws null pointer exception if brand or city could not be created
            throw new ResourceBadRequestException();
        }
    }
}
