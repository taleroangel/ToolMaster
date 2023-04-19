import { Brand } from "./brand"
import { City } from "./city"

/**
 * Interfaz de la representación de la respuesta del
 * servidor en formato JSON de una herramienta
 */
export interface Tool {
  id: number
  name: string
  brand: Brand
  description: string
  image: string
  price: number
  cities: City[]
  units: number
}
