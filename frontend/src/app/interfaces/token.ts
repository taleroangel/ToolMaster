/**
 * Interfaz mediante la cual se representa la respuesta de autenticación por tokens del servidor
 */
export interface Token {
  /**
   * Nombre de usuario dueño del token
   */
  username: string,

  /**
   * Token JWT
   */
  token: string,
}
