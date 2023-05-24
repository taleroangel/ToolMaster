package edu.puj.toolmaster.users;

import edu.puj.toolmaster.users.controller.UserController;
import edu.puj.toolmaster.users.entities.User;
import edu.puj.toolmaster.users.services.UserService;
import edu.puj.toolmaster.users.exceptions.EntityAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @InjectMocks
    UserController controller;

    @Mock
    UserService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Esta prueba verifica que el método getAllUsers del controlador devuelve el
     * resultado esperado. En la prueba, creamos una página de usuarios y le decimos
     * a Mockito que, cuando se llame al método getAllUsers del servicio, devuelva esa
     * página. Luego, llamamos al método getAllUsers del controlador y comprobamos que
     * el resultado sea igual a la página de usuarios que creamos. El objetivo de esta
     * prueba es asegurarse de que el controlador está devolviendo correctamente los
     * usuarios que recibe del servicio.
     */
    @Test
    public void testGetAllUsers() {
        User user = new User();
        Page<User> page = new PageImpl<>(Arrays.asList(user));
        when(service.getAllUsers(any())).thenReturn(page);
        Page<User> result = controller.getAllUsers(0, 10, "name");
        assertEquals(page, result);
    }

    /**
     * Similar a la prueba anterior, esta prueba verifica que el método
     * searchUsersByName del controlador devuelve el resultado esperado.
     * Creamos una página de usuarios y le decimos a Mockito que, cuando
     * se llame al método userByNameLike del servicio con cualquier cadena
     * y PageRequest, devuelva esa página. Luego, llamamos al método
     * searchUsersByName del controlador y comprobamos que el resultado
     * sea igual a la página de usuarios que creamos. El objetivo de esta
     * prueba es asegurarse de que el controlador está devolviendo correctamente
     * los usuarios que recibe del servicio cuando se realiza una búsqueda por nombre.
     */
    @Test
    public void testSearchUsersByName() {
        User user = new User();
        Page<User> page = new PageImpl<>(Arrays.asList(user));
        when(service.userByNameLike(anyString(), any())).thenReturn(page);
        Page<User> result = controller.searchUsersByName(0, 10, "name", "test");
        assertEquals(page, result);
    }

    /**
     * Esta prueba verifica que el método getUserById del controlador devuelve
     * el resultado esperado. Creamos un usuario y le decimos a Mockito que,
     * cuando se llame al método getUserById del servicio con cualquier número
     * largo, devuelva ese usuario. Luego, llamamos al método getUserById del
     * controlador y comprobamos que el resultado sea igual al usuario que
     * creamos. El objetivo de esta prueba es asegurarse de que el controlador
     * está devolviendo correctamente el usuario que recibe del servicio cuando
     * se busca por ID.
     */
    @Test
    public void testGetUserById() {
        User user = new User();
        when(service.getUserById(anyLong())).thenReturn(user);
        User result = controller.getUserById(1L);
        assertEquals(user, result);
    }

    /**
     * Esta prueba verifica que el método createNewUser del controlador
     * devuelve el resultado esperado cuando se crea un nuevo usuario con éxito.
     * Creamos un usuario y le decimos a Mockito que, cuando se llame al método
     * addNewUser del servicio con cualquier usuario, devuelva ese usuario.
     * Luego, llamamos al método createNewUser del controlador y comprobamos
     * que el resultado sea igual al usuario que creamos. El objetivo de esta
     * prueba es asegurarse de que el controlador está devolviendo correctamente
     * el nuevo usuario que recibe del servicio.
     */
    @Test
    public void testCreateNewUser() {
        User user = new User();
        when(service.addNewUser(any())).thenReturn(user);
        ResponseEntity<User> result = controller.createNewUser(user);
        assertEquals(user, result.getBody());
    }

    /**
     * Esta prueba verifica que el método createNewUser del controlador maneja
     * correctamente la situación en la que se intenta crear un usuario que ya existe.
     * Creamos un usuario y le decimos a Mockito que, cuando se llame al método addNewUser
     * del servicio con cualquier usuario, lance una EntityAlreadyExistsException con ese usuario.
     * Luego, llamamos al método createNewUser del controlador y comprobamos que el resultado sea
     * igual al usuario que creamos. El objetivo de esta prueba es asegurarse de que el controlador
     * está manejando correctamente la excepción y devolviendo el recurso existente.
     */
    @Test
    public void testCreateNewUserAlreadyExists() {
        User user = new User();
        when(service.addNewUser(any())).thenThrow(new EntityAlreadyExistsException(user));
        ResponseEntity<User> result = controller.createNewUser(user);
        assertEquals(user, result.getBody());
    }

    /**
     * Esta prueba verifica que el método deleteUserById del controlador llama al
     * método correspondiente del servicio con los parámetros correctos.
     * Le decimos a Mockito que, cuando se llame al método deleteUserById del
     * servicio con cualquier número largo, no haga nada. Luego, llamamos al
     * método deleteUserById del controlador y verificamos que se haya llamado
     * al método deleteUserById del servicio con el número largo que pasamos.
     * El objetivo de esta prueba es asegurarse de que el controlador está
     * llamando correctamente al servicio para eliminar un usuario.
     */
    @Test
    public void testDeleteUserById() {
        doNothing().when(service).deleteUserById(anyLong());
        controller.deleteUserById(1L);
        verify(service, times(1)).deleteUserById(1L);
    }

    /**
     * Esta prueba verifica que el método updateUserById del controlador
     * devuelve el resultado esperado. Creamos un usuario y le decimos a Mockito que,
     * cuando se llame al método updateUserById del servicio con cualquier número largo
     * y usuario, devuelva ese usuario. Luego, llamamos al método updateUserById del
     * controlador y comprobamos que el resultado sea igual al usuario que creamos.
     * El objetivo de esta prueba es asegurarse de que el controlador está devolviendo
     * correctamente el usuario actualizado que recibe del servicio.
     */
    @Test
    public void testUpdateUserById() {
        User user = new User();
        when(service.updateUserById(anyLong(), any())).thenReturn(user);
        User result = controller.updateUserById(1L, user);
        assertEquals(user, result);
    }

    /**
     * Similar a la prueba anterior, esta prueba verifica que el método partialUserUpdate
     * del controlador devuelve el resultado esperado. Creamos un usuario y le
     * decimos a Mockito que, cuando se llame al método partialUserUpdateById del servicio
     * con cualquier número largo y usuario, devuelva ese usuario. Luego, llamamos al
     * método partialUserUpdate del controlador y comprobamos que el resultado sea igual
     * al usuario que creamos. El objetivo de esta prueba es asegurarse de que el controlador
     * está devolviendo correctamente el usuario parcialmente actualizado que recibe del servicio.
     */
    @Test
    public void testPartialUserUpdate() {
        User user = new User();
        when(service.partialUserUpdateById(anyLong(), any())).thenReturn(user);
        User result = controller.partialUserUpdate(1L, user);
        assertEquals(user, result);
    }
}