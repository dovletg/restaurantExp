import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserAuthGuard } from '../guards/authGuard/user-auth.guard';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { OrderComponent } from './components/order/order.component';
import { ViewMealComponent } from './components/view-meal/view-meal.component';
import { UserComponent } from './user.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent,canActivate:[UserAuthGuard] },
  { path: 'order', component: OrderComponent,canActivate:[UserAuthGuard] },
  { path: 'view-meal/:id', component: ViewMealComponent,canActivate:[UserAuthGuard] },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
