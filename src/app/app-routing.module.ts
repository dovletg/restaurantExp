import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NoAuthGuard } from './guards/noAuthGuard/no-auth.guard';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { HomeComponent } from './home/home.component';
import { ContactComponent } from './contact/contact.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent  ,canActivate:[NoAuthGuard]},
  { path: 'contact', component: ContactComponent  ,canActivate:[NoAuthGuard]},
  { path: 'login', component: LoginComponent  ,canActivate:[NoAuthGuard]},
  { path: 'register', component: SignupComponent,canActivate:[NoAuthGuard] },
  { path: 'user', loadChildren: () => import('./user/user.module').then(m => m.UserModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
