import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

/**
 * Componente de inición de sesión o de cierre de sesión
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  /**
   * Controlador del form de inicio de sesión
   */
  checkoutForm = new FormGroup({
    user: new FormControl(),
    password: new FormControl()
  })

  /**
   * Constructor con la inyección de dependencias
   * @param authService Servicio de autenticación
   * @param router Enrutador para cambiar la ruta una vez se autentique
   */
  constructor(public authService: AuthService, public router: Router) { }

  /**
   * Obtener el estado de la autenticación
   * @return True si está autenticado
   */
  get authenticated(): boolean {
    return this.authService.authenticated;
  }

  /**
   * Función mediante la cual se entregan los resultados del formulario
   * al backend para realizar la autenticación
   */
  async onSubmit(): Promise<void> {
    // Obtener los parámetros
    const userParam: string = this.checkoutForm.value.user;
    const passParam: string = this.checkoutForm.value.password;

    // Validar los campos
    if (!this.checkoutForm.valid) {
      alert("Debe llenar los campos!")
      return
    }

    // Hacer la petición
    let loginPromise = new Promise<void>((resolve, reject) => {
      this.authService.login(userParam, passParam, () => {
        this.router.navigateByUrl('/home')
        resolve()
      }, () => {
        alert("Error!, usuario o contraseña son incorrectos")
        reject()
      })
    })

    // Reiniciar el form
    this.checkoutForm.reset()

    return await loginPromise;
  }

  /**
   * Función mediante la cual se cierra sesión en caso de que el usuario esté
   * autenticado
   */
  logOut(navigateToHome: boolean = false) {
    this.authService.logout()
    alert("Sesión cerrada correctamente")

    if (navigateToHome)
      this.router.navigateByUrl('/home')
  }
}
