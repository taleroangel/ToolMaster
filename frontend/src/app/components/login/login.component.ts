import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

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

  constructor(private loginService: LoginService, public router: Router) { }

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
    this.loginService.login(userParam, passParam).subscribe(
      data => {
        this.loginService.setToken(data.token)
        this.router.navigateByUrl('/tools')
      },
      error => {
        console.error(error);
        alert("Error!, usuario o contraseña son incorrectos")
      }
    )

    // Reiniciar el form
    this.checkoutForm.reset()
  }
}
