/**
 * Interfaz mediante la cual se representa la respuesta de autenticación del servidor
 */
export interface Auth {
  login: string;
  password: string;
  token: string;
}
