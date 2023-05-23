import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

/**
 * Vista principal donde se muestran las opciones de listar las herramientas
 * o ver el listado de usuarios en el sistema
 */
@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent {
  /**
   * Constructor del componente
   * @param AuthService Servicio de autenticación para mostrar una bienvenida de usuario
   */
  constructor(public AuthService: AuthService) { }
}
