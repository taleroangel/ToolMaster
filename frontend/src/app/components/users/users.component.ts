import { Component, OnInit } from '@angular/core';
import { Pageable } from 'src/app/interfaces/pageable';
import { UserService, UserSort } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';

/**
 * Vista del listado de usuarios en el sistema. Se debe estar
 * autenticado para poder manipular estos usuarios
 */
@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  /**
   * Alias al enum UserSort para uso interno la plantilla
   */
  US = UserSort;

  /**
   * Variable para saber si ya está lista la información para ser mostrada
   * mientas sea falso se mostrará un spinner de carga
   */
  isReady = false;

  /**
   * Información sobre la paginación de usuarios para mostrar la página actual
   */
  pagination!: Pageable<User>;

  /**
   * Listado de los usuarios a mostrar
   */
  users!: User[];

  /**
   * Página actual para mostrar en la paginación
   */
  currentPage: number = 0;

  /**
   * Criterio de ordenamiento actual
   */
  currentSort: UserSort = UserSort.NONE;

  /**
   * Variable para almacenar el nombre del usuario a buscar,
   * está adjunta mediante un [ngModel] al input de búsqueda
   */
  searchName: string = "";

  /**
   * Constructor con los servicios inyectados
   * @param userService Servicio de usuarios
   * @param authService Servicio de autenticación
   */
  constructor(
    private userService: UserService,
    private authService: AuthService) { }

  /**
   * Cargar contenido una vez se inicie la página
   */
  ngOnInit(): void {
    // Obtener contenidos sólo si está autenticado
    if (this.authService.authenticated)
      this.fetchUsers()
  }

  /**
   * Verificar si se está autenticado
   * @returns True si está autenticado
   */
  get authenticated(): boolean {
    return this.authService.authenticated
  }

  /**
   * Obtener los usuarios utilizando las variables de ordenamiento y paginado
   */
  fetchUsers(): void {
    this.userService.searchAllUsers(this.currentSort, this.currentPage).subscribe({
      next: data => {
        this.pagination = data;
        this.users = data.content;
        this.isReady = true;
      },
      error: error => {
        console.error(error);
        alert("Error!, No se ha podido conectar con el servidor")
      }
    })
  }

  /**
   * Obtener los usuarios cuyo nombre coincida o incluya el especificado
   */
  searchByName(): void {
    if (this.searchName.length == 0)
      return this.fetchUsers()

    this.userService.searchByName(this.searchName, this.currentSort, this.currentPage).subscribe({
      next: data => {
        this.pagination = data;
        this.users = data.content;
        this.isReady = true;
      },
      error: error => {
        console.error(error);
        alert("Error!, No se ha podido conectar con el servidor")
      }
    })
  }

  /**
   * Borrar un usuario dado su identificado
   * @param id Identificación del usuario a borrar
   */
  deleteUser(id: number): void {
    this.userService.deleteById(id).subscribe({
      next: _ => {
        alert("Eliminado correctamente!")
        this.fetchUsers()
      },
      error: error => {
        console.error(error);
        alert("Error!, No se ha podido conectar con el servidor")
      }
    })
  }

  /**
   * Cambiar el criterio de ordenamiento y realizar una nueva petición
   * @param sort Criterio de ordenamiento
   */
  sortBy(sort: UserSort): void {
    this.currentSort = sort;
    this.fetchUsers()
  }

  /**
   * Cambiar la paginación a la siguiente página
   */
  goNextPage(): void {
    if (!this.pagination.last) {
      this.currentPage += 1
      this.fetchUsers()
    }
  }

  /**
   * Cambiar la paginación a la página anterior
   */
  goPreviousPage(): void {
    if (!this.pagination.first) {
      this.currentPage -= 1
      this.fetchUsers()
    }
  }
}
