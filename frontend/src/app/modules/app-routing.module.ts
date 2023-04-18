import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { WelcomeComponent } from '../components/welcome/welcome.component';
import { LoginComponent } from '../components/login/login.component';
import { ToolsComponent } from '../components/tools/tools.component';

const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'tools', component: ToolsComponent },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
