package edu.puj.toolmaster.users;

import edu.puj.toolmaster.users.entities.City;
import edu.puj.toolmaster.users.entities.User;
import edu.puj.toolmaster.users.entities.auth.Auth;
import edu.puj.toolmaster.users.exceptions.EntityAlreadyExistsException;
import edu.puj.toolmaster.users.exceptions.ResourceBadRequestException;
import edu.puj.toolmaster.users.exceptions.ResourceNotFoundException;
import edu.puj.toolmaster.users.persistance.AuthRepository;
import edu.puj.toolmaster.users.persistance.CityRepository;
import edu.puj.toolmaster.users.persistance.UserRepository;
import edu.puj.toolmaster.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrialUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private AuthRepository authRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService();
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        ReflectionTestUtils.setField(userService, "cityRepository", cityRepository);
        ReflectionTestUtils.setField(userService, "authRepository", authRepository);
    }

    @Test
    public void testGetAllUsers() {
        Page<User> userPage = new PageImpl<>(Collections.singletonList(new User()));
        when(userRepository.findAllByActiveTrue(any(Pageable.class))).thenReturn(userPage);

        Page<User> result = userService.getAllUsers(Pageable.unpaged());

        assertEquals(userPage, result);
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(user, result);
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }


//    @Test
//    public void testAddNewUser_EntityAlreadyExistsException() {
//        User user = new User();
//        when(userRepository.findOne(any())).thenReturn(Optional.of(user));
//
//        assertThrows(EntityAlreadyExistsException.class, () -> userService.addNewUser(user));
//    }

    @Test
    public void testAddNewUser_Success() {
        City city = new City(1, "Bogotá");
        User user = new User().withCity(city).withId(1L).withName("Lorem").withLastName("Ipsum");

        when(userRepository.findOne(any())).thenReturn(Optional.empty());
        when(cityRepository.findById(any())).thenReturn(Optional.of(city));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(authRepository.save(any(Auth.class))).thenReturn(null);

        User result = userService.addNewUser(user);
        assertEquals(user, result);
    }

    @Test
    public void testUserByNameLike() {
        Page<User> userPage = new PageImpl<>(Collections.singletonList(new User()));
        when(userRepository.findAll((Specification<User>) any(), any(Pageable.class))).thenReturn(userPage);

        Page<User> result = userService.userByNameLike("test", Pageable.unpaged());

        assertEquals(userPage, result);
    }

    @Test
    public void testParseUser() {
        User user = new User();
        City city = new City(1, "Bogotá");
        user = user.withCity(city);
        when(cityRepository.findById(any())).thenReturn(Optional.of(city));
        when(cityRepository.save(any(City.class))).thenReturn(city);

        User result = userService.parseUser(user);

        assertEquals(city, result.getCity());
    }

    @Test
    public void testParseUser_ResourceBadRequestException() {
        User user = new User();
        City city = new City(1, "Bogotá");
        user = user.withCity(city);

        when(cityRepository.findById(any())).thenReturn(Optional.empty());
        when(cityRepository.save(any())).thenThrow(ResourceBadRequestException.class);

        User finalUser = user;
        assertThrows(ResourceBadRequestException.class, () -> userService.parseUser(finalUser));
    }

    @Test
    public void testDeleteUserById() {
        User user = new User();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        doNothing().when(authRepository).deleteById(any(Long.class));
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertDoesNotThrow(() -> userService.deleteUserById(1L));
    }

    @Test
    public void testDeleteUserById_ResourceNotFoundException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUserById(1L));
    }

    @Test
    public void testUpdateUserById() {
        User user = new User();
        City city = new City(1, "Bogotá");
        user = user.withCity(city).withId(1L);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUserById(1L, user);

        assertEquals(user, result);
    }

    @Test
    public void testUpdateUserById_ResourceBadRequestException() {
        User user = new User();
        City city = new City(1, "Bogotá");

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(cityRepository.findById(any())).thenReturn(Optional.empty());

        User finalUser = user;
        assertThrows(ResourceBadRequestException.class, () -> userService.updateUserById(1L, finalUser));
    }

    @Test
    public void testPartialUserUpdateById() {
        User user = new User();
        User existingUser = new User();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.partialUserUpdateById(1L, user);

        assertEquals(user, result);
    }

    @Test
    public void testPartialUserUpdateById_ResourceBadRequestException() {
        City city = new City(null, "Bogotá");
        User user = new User().withCity(city);

        User existingUser = new User().withId(1L);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(existingUser));
        when(cityRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceBadRequestException.class, () -> userService.partialUserUpdateById(1L, user));
    }

}