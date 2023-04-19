import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Brand } from '../models/brand';
import { HttpClient } from '@angular/common/http';

/**
 * Servicio para obtener las marcas desde el backend
 */
@Injectable({
  providedIn: 'root'
})
export class BrandService {

  /**
   * Constructor por defecto con inyección de dependencias
   * @param http Cliente HTTP para realizar las peticiones REST
   */
  constructor(private http: HttpClient) { }

  /**
   * Obtener todas las marcas
   * @returns Observable con el listado de las marcas
   */
  fetchBrands(): Observable<Brand[]> {
    return this.http.get<Brand[]>("http://localhost:8081/api/brands/");
  }
}
