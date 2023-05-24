package edu.puj.toolmaster.users;

import edu.puj.toolmaster.users.controller.UserController;
import edu.puj.toolmaster.users.entities.User;
import edu.puj.toolmaster.users.services.UserService;
import edu.puj.toolmaster.users.services.exceptions.EntityAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    @InjectMocks
    UserController controller;

    @Mock
    UserService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User user = new User();
        Page<User> page = new PageImpl<>(Arrays.asList(user));
        when(service.getAllUsers(any())).thenReturn(page);
        Page<User> result = controller.getAllUsers(0, 10, "name");
        assertEquals(page, result);
    }

    @Test
    public void testSearchUsersByName() {
        User user = new User();
        Page<User> page = new PageImpl<>(Arrays.asList(user));
        when(service.userByNameLike(anyString(), any())).thenReturn(page);
        Page<User> result = controller.searchUsersByName(0, 10, "name", "test");
        assertEquals(page, result);
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        when(service.getUserById(anyLong())).thenReturn(user);
        User result = controller.getUserById(1L);
        assertEquals(user, result);
    }

    @Test
    public void testCreateNewUser() {
        User user = new User();
        when(service.addNewUser(any())).thenReturn(user);
        ResponseEntity<User> result = controller.createNewUser(user);
        assertEquals(user, result.getBody());
    }

    @Test
    public void testCreateNewUserAlreadyExists() {
        User user = new User();
        when(service.addNewUser(any())).thenThrow(new EntityAlreadyExistsException(user));
        ResponseEntity<User> result = controller.createNewUser(user);
        assertEquals(user, result.getBody());
    }

    @Test
    public void testDeleteUserById() {
        doNothing().when(service).deleteUserById(anyLong());
        controller.deleteUserById(1L);
        verify(service, times(1)).deleteUserById(1L);
    }

    @Test
    public void testUpdateUserById() {
        User user = new User();
        when(service.updateUserById(anyLong(), any())).thenReturn(user);
        User result = controller.updateUserById(1L, user);
        assertEquals(user, result);
    }

    @Test
    public void testPartialUserUpdate() {
        User user = new User();
        when(service.partialUserUpdateById(anyLong(), any())).thenReturn(user);
        User result = controller.partialUserUpdate(1L, user);
        assertEquals(user, result);
    }
}