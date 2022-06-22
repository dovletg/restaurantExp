import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MealService } from '../../user-services/meal/meal.service';

@Component({
  selector: 'app-view-meal',
  templateUrl: './view-meal.component.html',
  styleUrls: ['./view-meal.component.scss']
})
export class ViewMealComponent implements OnInit {

  mealId: any = this.activatedroute.snapshot.params['id'];
  meal:any;
  isSpinning:boolean = false;

  constructor(private activatedroute: ActivatedRoute,
                private mealService: MealService) { }

  ngOnInit(): void {
    this.getMealById();
  }

  getMealById(){
    this.mealService.getMealByid(this.mealId).subscribe(res =>{
      console.log(res);
      this.meal = res.meals[0];
     
    })
  }

}
