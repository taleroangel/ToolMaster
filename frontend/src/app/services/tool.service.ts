import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pageable } from 'src/app/interfaces/pageable';
import { Tool } from 'src/app/models/tool';
import { AuthService } from './auth.service';

/**
 * Servicio para obtener las herramientas desde el Backend
 */
@Injectable({
  providedIn: 'root'
})
export class ToolService {

  /**
   * Constructor por defecto con inyección de dependencias
   * @param http Cliente HTTP para realizar peticiones REST
   * @param authService Servicio de autenticación para rutas protegidas
   */
  constructor(public http: HttpClient, public authService: AuthService) { }

  /**
   * Buscar todas las herramientas, con paginación
   * @param sort Criterio de ordenamiento
   * @param page Página, empieza desde 0
   * @returns Observable de rxjs con la petición
   */
  searchAllTools(sort: ToolSort = ToolSort.NONE, page: number = 0): Observable<Pageable<Tool>> {
    const params = new HttpParams().set('sort', sort).set('page', page);
    return this.http.get<Pageable<Tool>>("http://localhost:8081/api/tools/", { params: params })
  }

  /**
   * Buscar todas las herramientas con el nombre especificado
   * @param sort Criterio de ordenamiento
   * @param page Página, empieza desde 0
   * @returns Observable de rxjs con la petición
   */
  searchByName(name: string, sort: ToolSort = ToolSort.NONE, page: number = 0): Observable<Pageable<Tool>> {
    const params = new HttpParams().set('sort', sort).set('page', page);
    return this.http.get<Pageable<Tool>>(`http://localhost:8081/api/tools/search/name/${name}`, { params: params })
  }

  /**
   * Buscar todos las herramientas con la marca especificada
   * @param sort Criterio de ordenamiento
   * @param page Página, empieza desde 0
   * @returns Observable de rxjs con la petición
   */
  searchByBrand(brand: string, sort: ToolSort = ToolSort.NONE, page: number = 0): Observable<Pageable<Tool>> {
    const params = new HttpParams().set('sort', sort).set('page', page);
    return this.http.get<Pageable<Tool>>(`http://localhost:8081/api/tools/search/brand/${brand}`, { params: params })
  }

  /**
   * Borrar la herramienta dado su ID
   * @param id ID de la herramienta a borrar
   * @returns Observable de rxjs con la petición
   */
  eraseToolById(id: number): Observable<void> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.token}`);
    return this.http.delete<void>(`http://localhost:8081/api/tools/${id}`, { headers: headers });
  }
}

/**
 * Enumeración que describe los criterios de ordenamiento de una herramienta
 */
export enum ToolSort {
  NONE = '',
  PRICE = 'price',
  NAME = 'name',
  BRAND = 'brand.name',
  CITY = 'cities.name',
}
