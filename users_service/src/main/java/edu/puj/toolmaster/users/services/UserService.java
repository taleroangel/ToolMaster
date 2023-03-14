package edu.puj.toolmaster.users.services;

import edu.puj.toolmaster.users.entities.City;
import edu.puj.toolmaster.users.entities.User;
import edu.puj.toolmaster.users.persistance.CityRepository;
import edu.puj.toolmaster.users.persistance.UserRepository;
import edu.puj.toolmaster.users.services.exceptions.EntityAlreadyExistsException;
import edu.puj.toolmaster.users.services.exceptions.ResourceBadRequestException;
import edu.puj.toolmaster.users.services.exceptions.ResourceNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAllByActiveTrue(pageable);
    }

    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public User parseUser(User user) throws ResourceBadRequestException {
        return user.withCity(parseUserCity(user));
    }

    public City parseUserCity(User user) throws ResourceBadRequestException {
        return user.getCity().getId() == null ? cityRepository.findByName(user.getCity().getName())
                                                        .orElseThrow(ResourceNotFoundException::new)
                       : cityRepository.findById(user.getCity().getId()
        ).orElseGet(() -> cityRepository.save(user.getCity()));
    }

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
            return userRepository.save(fixedUser);

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            // Throws null pointer exception if brand or city could not be created
            throw new ResourceBadRequestException();
        }
    }

    public void deleteUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        // Logical Delete
        user = user.withActive(Boolean.FALSE);
        userRepository.save(user);
    }

    public User updateUserById(Long id, User user) throws ResourceBadRequestException {
        try {
            // Find already existing user
            User findUser = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
            // Parse the new user
            User parsedUser = parseUser(user);
            // Save the changes
            return userRepository.save(parsedUser.withId(findUser.getId()));
        } catch (Exception e) {
            // Throws null pointer exception if brand or city could not be created
            throw new ResourceBadRequestException();
        }
    }

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
            // Save the changes
            return userRepository.save(overrideUser);
        } catch (Exception e) {
            // Throws null pointer exception if brand or city could not be created
            throw new ResourceBadRequestException();
        }
    }
}
