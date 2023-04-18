import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  checkoutForm = new FormGroup({
    user: new FormControl(),
    password: new FormControl()
  })

  constructor(public authService: AuthService, private router: Router) { }

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
    this.authService.login(userParam, passParam).subscribe(
      {
        next: data => {
          this.authService.setToken(data.token)
          this.router.navigateByUrl('/home')
        },
        error: error => {
          console.error(error);
          alert("Error!, usuario o contraseña son incorrectos")
        }
      }
    )

    // Reiniciar el form
    this.checkoutForm.reset()
  }

  logOut() {
    this.authService.logout()
    alert("Sesión cerrada correctamente")
    this.router.navigateByUrl('/home')
  }
}
