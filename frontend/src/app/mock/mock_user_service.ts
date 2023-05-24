import { Injectable } from "@angular/core";
import { UserService, UserSort } from "../services/user.service";
import { Observable } from "rxjs";
import { Pageable } from "../interfaces/pageable";
import { User } from "../models/user";

/**
 * Servicio de usuarios mockeado
 */
@Injectable()
export class MockUserService extends UserService {

  /**
   * Borrar un usuario dado su ID
   * @param id ID del usuario, acepta si mayor o igual a 0 o rechaza si menor a 0
   * @returns Observable con error o siguiente
   */
  override deleteById(id: number): Observable<any> {
    return new Observable((observe) => {
      if (id < 0) {
        observe.error()
      } else {
        observe.next()
      }
    })
  }

  /**
   * Buscar todos los usuarios
   * @param sort Criterio de ordenamiento
   * @param page Página
   * @returns Página de usuarios
   */
  override searchAllUsers(sort: UserSort, page?: number): Observable<Pageable<User>> {
    return new Observable((observable) => {
      observable.next(
        {
          "content": [
            {
              "id": 1002382383,
              "name": "John",
              "lastName": "Doe",
              "birthDate": "1990-01-01T00:00:00.000+00:00",
              "city": {
                "id": 1,
                "name": "Bogotá"
              },
              "active": true,
              "username": "john_doe"
            },
            {
              "id": 1192734759,
              "name": "Jane",
              "lastName": "Doe",
              "birthDate": "1992-05-12T00:00:00.000+00:00",
              "city": {
                "id": 2,
                "name": "Medellin"
              },
              "active": true,
              "username": "jane_doe"
            },
            {
              "id": 9218274590,
              "name": "Alice",
              "lastName": "Johnson",
              "birthDate": "1998-06-30T00:00:00.000+00:00",
              "city": {
                "id": 3,
                "name": "Cali"
              },
              "active": true,
              "username": "alice_johnson"
            }
          ],
          "pageable": {
            "sort": {
              "empty": false,
              "sorted": true,
              "unsorted": false
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 10,
            "paged": true,
            "unpaged": false
          },
          "totalPages": 1,
          "totalElements": 3,
          "last": true,
          "size": 10,
          "number": 0,
          "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
          },
          "numberOfElements": 3,
          "first": true,
          "empty": false
        }
      )
    })
  }
}
