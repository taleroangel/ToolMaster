/**
 * Interfaz mediante la cual se representan las respuestas paginadas del servidor
 */
export interface Pageable<T> {
  /**
   * Contenido de la paginación
   */
  content: T[]

  /**
   * Propiedades de paginado
   */
  pageable: {
    /**
   * Criterio de ordenamiento
   */
    sort: {
      /**
       * No hay criterio
       */
      empty: boolean

      /**
       * No está ordenado
       */
      unsorted: boolean

      /**
       * Está ordenado
       */
      sorted: boolean
    }

    /**
     * Offset de elementos en la página
     */
    offset: number

    /**
     * Número de la página
     */
    pageNumber: number

    /**
     * Tamaño de la página
     */
    pageSize: number

    /**
     * Está paginado
     */
    paged: boolean

    /**
     * No está paginado
     */
    unpaged: boolean
  }

  /**
   * Numero total de páginas
   */
  totalPages: number

  /**
   * Número total de elementos
   */
  totalElements: number

  /**
   * Es última página?
   */
  last: boolean

  /**
   * Cantidad de elementos en la página
   */
  size: number

  /**
   * Número de página
   */
  number: number

  /**
   * Criterio de ordenamiento
   */
  sort: {
    /**
     * No hay criterio
     */
    empty: boolean

    /**
     * No está ordenado
     */
    unsorted: boolean

    /**
     * Está ordenado
     */
    sorted: boolean
  }

  /**
   * Cantidad de elementos por página
   */
  numberOfElements: number

  /**
   * Es primera página?
   */
  first: boolean

  /**
   * La página está vacía?
   */
  empty: boolean
}
