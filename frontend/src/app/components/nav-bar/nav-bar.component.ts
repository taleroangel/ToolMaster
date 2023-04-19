import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

/**
 * Componente de barra superior para la navegación
 */
@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent {
  /**
   * Constructor con la inyección de dependencias
   * @param authService Servicio de autenticación
   */
  constructor(private authService: AuthService) { }

  /**
   * Verificar si el usuario está autenticado para cambiar el botón de login
   * por uno de logout
   * @return True si se encuentra autenticado
   */
  get authenticated(): boolean {
    return this.authService.authenticated;
  }
}
