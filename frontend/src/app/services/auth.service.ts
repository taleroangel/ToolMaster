import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Token } from '../interfaces/token';

/**
 * Servicio que se encarga de controlar la autenticación y autorización desde
 * el Backend con los JWT almacenados en cookies
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  /**
   * Constructor por defecto con inyección de dependencias
   * @param http Cliente HTTP para las peticiones rest
   * @param cookies Servicio de almacenamiento de cookies
   */
  constructor(
    private http: HttpClient,
    private cookies: CookieService
  ) { }


  /**
   * Iniciar sesión en el backend y almacenar el Json Web Token (JWT)
   * @param user Nombre de usuario
   * @param password Contraseña
   * @param onSuccessCallback Callback en caso de que sea exitosa la autenticación
   * @param onFailureCallback Callback en caso de que falle la autenticación
   */
  login(user: string, password: string, onSuccessCallback: Function, onFailureCallback: Function): void {
    this.http.post<Token>("http://localhost:8080/api/auth/login", {
      username: user,
      password: password
    }).subscribe(
      {
        next: data => {
          this.token = data.token;
          this.username = data.username;
          onSuccessCallback()
        },
        error: error => {
          console.error(error);
          onFailureCallback()
        }
      }
    )
  }

  /**
   * Cerrar sesión eliminando las cookies almacenadas
   */
  logout() {
    this.cookies.delete('token')
    this.cookies.delete('username')
  }

  /**
   * Setter privado para establecer el nombre de usuario en una cookie
   */
  private set username(username: string) {
    this.cookies.set('username', username)
  }

  /**
   * Obtener el nombre de usuario, vacío si no hay usuario autenticado
   */
  public get username(): string {
    return this.cookies.get('username')
  }

  /**
   * Setter privado para establecer el token de autenticación
   */
  private set token(token: string) {
    this.cookies.set('token', token)
  }

  /**
   * Obtener el token JWT para la autenticación Bearer
   */
  public get token(): string {
    return this.cookies.get('token')
  }

  /**
   * Verificar si se está autenticado
   */
  get authenticated(): boolean {
    return this.token.length != 0
  }
}
