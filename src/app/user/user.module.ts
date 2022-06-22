import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DemoNgZorroAntdModule } from '../DemoNgZorroAntdModule';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ViewMealComponent } from './components/view-meal/view-meal.component';
import { OrderComponent } from './components/order/order.component';


@NgModule({
  declarations: [
    UserComponent,
    DashboardComponent,
    ViewMealComponent,
    OrderComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    DemoNgZorroAntdModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class UserModule { }
