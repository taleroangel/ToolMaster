import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { WelcomeComponent } from '../components/welcome/welcome.component';
import { LoginComponent } from '../components/login/login.component';
import { ToolsComponent } from '../components/tools/tools.component';
import { UsersComponent } from '../components/users/users.component';

/**
 * Rutas de la SPA administradas por el Router
 */
const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: WelcomeComponent },
  { path: 'tools', component: ToolsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'users', component: UsersComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
