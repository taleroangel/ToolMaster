/**
 * Interfaz mediante la cual se representa la respuesta de autenticación del servidor
 */
export interface Auth {
  /**
   * Nombre de usuario
   */
  login: string;

  /**
   * Constraseñá
   */
  password: string;

  /**
   * Token de autenticación
   */
  token: string;
}
