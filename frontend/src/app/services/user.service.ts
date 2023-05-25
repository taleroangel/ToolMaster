import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Pageable } from '../interfaces/pageable';
import { User } from '../models/user';


/**
 * Servicio que recupera la información de los usuarios desde el Backend
 */
@Injectable({
  providedIn: 'root'
})
export class UserService {

  /**
   * Constructor por defecto con inyección de dependencias
   * @param http Cliente HTTP para realizar peticiones REST
   * @param authService Servicio de autenticación para recuperar el token
   */
  constructor(private http: HttpClient, public authService: AuthService) { }

  /**
   * Buscar todos los usuarios, con paginación
   * @param sort Criterio de ordenamiento
   * @param page Página, empieza desde 0
   * @returns Observable de rxjs con la petición
   */
  searchAllUsers(sort: UserSort, page: number = 0): Observable<Pageable<User>> {
    const params = new HttpParams().set('sort', sort).set('page', page).set('size', 1);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.token}`);
    return this.http.get<Pageable<User>>("http://localhost:8082/api/users/", { params: params, headers: headers })
  }

  /**
   * Buscar todos los usuarios con el nombre especificado
   * @param sort Criterio de ordenamiento
   * @param page Página, empieza desde 0
   * @returns Observable de rxjs con la petición
   */
  searchByName(name: string, sort: UserSort = UserSort.NONE, page: number = 0): Observable<Pageable<User>> {
    const params = new HttpParams().set('sort', sort).set('page', page);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.token}`);
    return this.http.get<Pageable<User>>(`http://localhost:8082/api/users/search/${name}`, { params: params, headers: headers })
  }

  /**
   * Borrar un usuario (desactivarlo) por su ID
   * @param id Número de identificación del usuario a borrar
   * @returns Observable con la petición
   */
  deleteById(id: number): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.token}`);
    return this.http.delete<Pageable<User>>(`http://localhost:8082/api/users/${id}`, { headers: headers })
  }
}

/**
 * Enumeración que describe los criterios de ordenamiento de un cliente
 */
export enum UserSort {
  NONE = '',
  ID = 'id',
  NAME = 'name',
  LASTNAME = 'lastName',
  BIRTHDATE = 'birthDate',
  CITY = 'city.name'
}
