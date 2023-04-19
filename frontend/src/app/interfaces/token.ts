/**
 * Interfaz mediante la cual se representa la respuesta de autenticación por tokens del servidor
 */
export interface Token {
  username: string,
  token: string,
}
