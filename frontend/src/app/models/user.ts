import { City } from "./city"

/**
 * Interfaz de representación de la respuesta en formato JSON del servidor
 */
export interface User {
  /**
   * ID del usuario
   */
  id: number

  /**
   * Nombre del usuario
   */
  name: string

  /**
   * Apellido del usuario
   */
  lastName: string

  /**
   * Fecha de nacimiento del usuario
   */
  birthDate: string

  /**
   * Ciudad del usuario
   */
  city: City

  /**
   * Está activo en el sistema
   */
  active: boolean

  /**
   * Nombre de usuario
   */
  username: string
}
