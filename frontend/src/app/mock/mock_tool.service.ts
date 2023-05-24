import { Observable, Observer } from "rxjs";
import { Pageable } from "../interfaces/pageable";
import { Tool } from "../models/tool";
import { ToolService, ToolSort } from "../services/tool.service";
import { Injectable } from "@angular/core";

/**
 * Servicio de herramientas Mock
 */
@Injectable()
export class MockToolService extends ToolService {

  /**
   * Listado de herramientas mock
   */
  static mockTools: Tool[] = [{
    id: 0,
    name: 'mock-tool',
    brand: {
      id: 0,
      name: 'mock-brand'
    },
    description: "This is a mock tool",
    price: 0,
    cities: [
      {
        id: 0,
        name: 'mock-city'
      }
    ],
    units: 1,
  }];

  /**
   * Buscar todas las herramientas
   * @param sort Criterio de ordenamiento
   * @param page Número de página
   * @returns
   */
  override searchAllTools(sort: ToolSort = ToolSort.NONE, page: number = 0): Observable<Pageable<Tool>> {
    return new Observable<Pageable<Tool>>((observer: Observer<Pageable<Tool>>) => {
      observer.next({
        content: MockToolService.mockTools,
        pageable: {
          sort: {
            empty: true,
            unsorted: true,
            sorted: false
          },
          offset: 0,
          pageNumber: 0,
          pageSize: 0,
          paged: false,
          unpaged: true,
        },
        totalPages: 0,
        totalElements: 0,
        last: false,
        size: 0,
        number: 0,
        sort: {
          empty: true,
          unsorted: true,
          sorted: false,
        },
        numberOfElements: 0,
        first: false,
        empty: true,
      })
    })
  }


  /**
   * Buscar una herramienta por su nombre
   * @param name Nombre a buscar
   * @param sort Criterio de ordenamiento
   * @param page Página
   * @returns Página de herramientas
   */
  override searchByName(name: string, sort: ToolSort = ToolSort.NONE, page: number = 0): Observable<Pageable<Tool>> {
    return new Observable<Pageable<Tool>>((observer: Observer<Pageable<Tool>>) => {
      observer.next({
        content: [],
        pageable: {
          sort: {
            empty: true,
            unsorted: true,
            sorted: false
          },
          offset: 0,
          pageNumber: 0,
          pageSize: 0,
          paged: false,
          unpaged: true,
        },
        totalPages: 0,
        totalElements: 0,
        last: false,
        size: 0,
        number: 0,
        sort: {
          empty: true,
          unsorted: true,
          sorted: false,
        },
        numberOfElements: 0,
        first: false,
        empty: true,
      })
    })
  }

  /**
   *
   * @param brand Nombre de la marca
   * @param sort Criterio de ordenamiento
   * @param page Número de la página
   * @returns Paginado de herramientas
   */
  override searchByBrand(brand: string, sort: ToolSort = ToolSort.NONE, page: number = 0): Observable<Pageable<Tool>> {
    return new Observable<Pageable<Tool>>((observer: Observer<Pageable<Tool>>) => {
      observer.next({
        content: [],
        pageable: {
          sort: {
            empty: true,
            unsorted: true,
            sorted: false
          },
          offset: 0,
          pageNumber: 0,
          pageSize: 0,
          paged: false,
          unpaged: true,
        },
        totalPages: 0,
        totalElements: 0,
        last: false,
        size: 0,
        number: 0,
        sort: {
          empty: true,
          unsorted: true,
          sorted: false,
        },
        numberOfElements: 0,
        first: false,
        empty: true,
      })
    })
  }

  /**
   * Mockea el borrar una herramienta dependiendo de su ID
   * @param id ID de la herramienta, acepta si positivo, rechaza si negativo
   * @returns Observable que siempre retorna next
   */
  override eraseToolById(id: number): Observable<void> {
    return new Observable((observer) => {
      if (id >= 0) {
        observer.next();
      } else {
        observer.error();
      }
    });
  }
}
