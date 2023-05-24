import { Injectable } from "@angular/core";
import { AuthService } from "../services/auth.service";

/**
 * Servicio de autenticación Mock para pruebas
 */
@Injectable()
export class MockAuthService extends AuthService {

  /**
   * Está autenticado?
   */
  private _authenticated: boolean = false;

  /**
   * Nombre de usuario actual
   */
  private _username: string = "";

  /**
   * Token actual
   */
  private _token: string = "";

  /**
   * Debe aceptar o rechazar autenticaciones
   */
  public shouldAccept = true;

  /**
   * Iniciar sesión mock, acepta o deniega en función de 'shouldAccept'
   * @param user Usuario
   * @param password Contraseña
   * @param onSuccessCallback Función llamada cuando es exitoso
   * @param onFailureCallback Función llamada cuando falla
   */
  override login(user: string, password: string, onSuccessCallback: Function, onFailureCallback: Function): void {
    if (this.shouldAccept) {
      this._username = user
      this._token = 'mock-token'
      this._authenticated = true
      onSuccessCallback.call(null)
    } else {
      this.logout()
      onFailureCallback.call(null)
    }
  }

  /**
   * Saber si se está autenticado
   */
  override get authenticated(): boolean {
    return this._authenticated;
  }

  /**
   * Desautenticarse
   */
  public override logout(): void {
    this._authenticated = false
  }

  /**
   * Obtener el token de autenticación
   */
  override get token(): string {
    return this._token;
  }

  /**
   * Obtener el nombre de usuario mock
   */
  override get username(): string {
    return this._username;
  }
}
