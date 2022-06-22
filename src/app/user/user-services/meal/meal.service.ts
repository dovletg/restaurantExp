import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of , tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MealService {

  constructor(private http: HttpClient) {  }


  getCategories(): Observable<any> {
    let url = `https://www.themealdb.com/api/json/v1/1/categories.php`;
  
    return this.http.get<any>(url)
    .pipe(
      tap((_) => console.log('Categories Data Fetched successfully')),
      catchError(this.handleError<[]>('Error Fetcing data', []))
    );
  }

  getMealsByCategories(cat): Observable<any> {
    let url = `https://www.themealdb.com/api/json/v1/1/filter.php?c=${cat}`;
  
    return this.http.get<any>(url)
    .pipe(
      tap((_) => console.log('Meals Data Fetched successfully')),
      catchError(this.handleError<[]>('Error Fetcing data', []))
    );
  }

  getMealByid(id): Observable<any> {
    let url = `https://www.themealdb.com/api/json/v1/1/lookup.php?i=${id}`;
  
    return this.http.get<any>(url)
    .pipe(
      tap((_) => console.log('Meal Data Fetched successfully')),
      catchError(this.handleError<[]>('Error Fetcing data', []))
    );
  }

  handleError<T>(operation = 'operation', result?: T): any {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  log(message: string): void {
    console.log(`User Auth Service: ${message}`);
  }

}
