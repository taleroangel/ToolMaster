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
  constructor(private authService: AuthService, private router: Router) { }

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
  onSubmit(): void {
    // Obtener los parámetros
    const userParam: string = this.checkoutForm.value.user;
    const passParam: string = this.checkoutForm.value.password;

    // Validar los campos
    if (!this.checkoutForm.valid) {
      alert("Debe llenar los campos!")
      return
    }

    // Hacer la petición
    this.authService.login(userParam, passParam, () => {
      this.router.navigateByUrl('/home')
    }, () => {
      alert("Error!, usuario o contraseña son incorrectos")
    })

    // Reiniciar el form
    this.checkoutForm.reset()
  }

  /**
   * Función mediante la cual se cierra sesión en caso de que el usuario esté
   * autenticado
   */
  logOut() {
    this.authService.logout()
    alert("Sesión cerrada correctamente")
    this.router.navigateByUrl('/home')
  }
}
