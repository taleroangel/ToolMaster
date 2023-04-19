import { City } from "./city"

/**
 * Interfaz de representación de la respuesta en formato JSON del servidor
 */
export interface User {
  id: number
  name: string
  lastName: string
  birthDate: string
  city: City
  active: boolean
  username: string
}
