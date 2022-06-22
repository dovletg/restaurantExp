import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { OrderService } from '../../user-services/order/order.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {

  isSpinning:boolean = false;
  order:any;
  validateForm!: FormGroup;

  constructor(private orderService:OrderService,
    private fb: FormBuilder,
    private notification: NzNotificationService,
    private router: Router) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      noPeople: [null, Validators.required],
      noTable: [null, Validators.required],
      date: [null, Validators.required],
    });
    this.getCart();
  }

  getCart(){
    this.orderService.getCartByUser().subscribe(res =>{
      console.log(res);
      this.order = res.data;
    })
  }

  updateQuantity(idMeal:any,quantity:any){
    if(quantity==0){
      this.notification
      .error(
        'ERROR',
        `Quantity Can't be 0`,
        { nzDuration: 5000 }
      )
    }
    else{
      this.orderService.updateQuantity(idMeal,quantity).subscribe(res =>{
        if(res.status == "OK"){
          this.getCart();
          this.notification
          .success(
            'SUCCESS',
            `Quantity Updated Successfully!!!`,
            { nzDuration: 5000 }
          );
        }else{
          this.notification
          .error(
            'ERROR',
            `${res.message}`,
            { nzDuration: 5000 }
          )
        }
      })
    }
  }

  placeOrder(){
    this.orderService.placeOrder(this.validateForm.value).subscribe(res =>{
      if(res.status == "CREATED"){
        this.router.navigateByUrl('/user/dashboard');
        this.notification
        .success(
          'SUCCESS',
          `Order Placed Successfully!!!`,
          { nzDuration: 5000 }
        );
      }else{
        this.notification
        .error(
          'ERROR',
          `${res.message}`,
          { nzDuration: 5000 }
        )
      }
    })
  }

  deleteMeal(id){
    this.orderService.deleteMealFromCart(id,this.order.id).subscribe(res =>{
      if(res.status == "OK"){
       this.getCart();
        this.notification
        .success(
          'SUCCESS',
          `Meal Deleted Successfully!!!`,
          { nzDuration: 5000 }
        );
      }else{
        this.notification
        .error(
          'ERROR',
          `${res.message}`,
          { nzDuration: 5000 }
        )
      }
    })
  }

}
