import { Component, OnInit } from '@angular/core';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { MealService } from '../../user-services/meal/meal.service';
import { OrderService } from '../../user-services/order/order.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  radioValue:any;
  categories:any;
  isSpinning:boolean = false;
  meals:any;

  constructor(private mealService:MealService,
              private orderService:OrderService,
              private notification: NzNotificationService,) { }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(){
    this.mealService.getCategories().subscribe(res =>{
      console.log(res);
      this.categories = res.categories;
    })
  }

  categorieChanged(value:any){
    console.log(value);
    this.mealService.getMealsByCategories(value).subscribe(res =>{
      console.log(res);
      this.meals = res.meals;
    })
  }

  addMealInCart(meal:any){
    let data = {
      mealId: meal.idMeal,
      title: meal.strMeal,
      quantity: 1,
      img: meal.strMealThumb
    }
    this.orderService.addMealToCart(data).subscribe(res =>{
      if(res.status == "CREATED"){
        this.notification
        .success(
          'SUCCESS',
          `Meal Added In Cart Successfully!!!`,
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
