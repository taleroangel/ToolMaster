import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pageable } from 'src/app/interfaces/pageable';
import { Tool } from 'src/app/models/tool';

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
   */
  constructor(private http: HttpClient) { }

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
