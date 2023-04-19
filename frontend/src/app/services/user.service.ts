import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Pageable } from '../interfaces/pageable';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, public authService: AuthService) { }

  searchAllUsers(sort: UserSort, page: number = 0): Observable<Pageable<User>> {
    const params = new HttpParams().set('sort', sort).set('page', page);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.token}`);
    return this.http.get<Pageable<User>>("http://localhost:8082/api/users/", { params: params, headers: headers })
  }

  searchByName(name: string, sort: UserSort = UserSort.NONE, page: number = 0): Observable<Pageable<User>> {
    const params = new HttpParams().set('sort', sort).set('page', page);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.token}`);
    return this.http.get<Pageable<User>>(`http://localhost:8082/api/users/search/${name}`, { params: params, headers: headers })
  }

  deleteById(id: number) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.token}`);
    return this.http.delete<Pageable<User>>(`http://localhost:8082/api/users/${id}`, { headers: headers })
  }
}

export enum UserSort {
  NONE = '',
  ID = 'id',
  NAME = 'name',
  LASTNAME = 'lastName',
  BIRTHDATE = 'birthDate',
  CITY = 'city.name'
}
