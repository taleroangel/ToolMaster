import { Brand } from "./brand"
import { City } from "./city"

/**
 * Interfaz de la representación de la respuesta del
 * servidor en formato JSON de una herramienta
 */
export interface Tool {
  /**
   * ID de la herramienta
   */
  id: number

  /**
   * Nombre de la herramienta
   */
  name: string

  /**
   * Marca de la herramienta
   */
  brand: Brand

  /**
   * Descripción de la herramienta
   */
  description: string

  /**
   * Imagen opcional de la herramienta
   */
  image?: string

  /**
   * Precio de la herramienta
   */
  price: number

  /**
   * Ciudades en las que está disponible la herramienta
   */
  cities: City[]

  /**
   * Unidades disponibles de la herramienta
   */
  units: number
}
