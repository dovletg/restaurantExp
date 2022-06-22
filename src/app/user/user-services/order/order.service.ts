import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';
import { environment } from 'src/environments/environment';

const BASIC_URL = environment['BASIC_URL'];

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient,
    private userStorageService : UserStorageService) { }


    addMealToCart(data: any): Observable<any> {
      const userId = UserStorageService.getUserId();
      return this.http
        .post<[]>(BASIC_URL+`api/cart/${userId}`,data,{
          headers: this.createAuthorizationHeader(),
        })
        .pipe(
          tap((_) => this.log('Added In successfully')),
          catchError(this.handleError<[]>('Error Adding In Cart', []))
        );
    }

    getCartByUser(): Observable<any> {
      const userId = UserStorageService.getUserId();
      return this.http
        .get<[]>(BASIC_URL+`api/cart/${userId}`,{
          headers: this.createAuthorizationHeader(),
        })
        .pipe(
          tap((_) => this.log('Got Cart successfully')),
          catchError(this.handleError<[]>('Error Getting Cart', []))
        );
    }

    placeOrder(data: any): Observable<any> {
      const userId = UserStorageService.getUserId();
      return this.http
        .post<[]>(BASIC_URL+`api/cart/place/${userId}`,data,{
          headers: this.createAuthorizationHeader(),
        })
        .pipe(
          tap((_) => this.log('Added In successfully')),
          catchError(this.handleError<[]>('Error Adding In Cart', []))
        );
    }

    updateQuantity(idMeal:any,quantity: any): Observable<any> {
      return this.http
        .get<[]>(BASIC_URL+`api/cart/updateQuantity/${idMeal}/${quantity}`,{
          headers: this.createAuthorizationHeader(),
        })
        .pipe(
          tap((_) => this.log('Quantity Updated successfully')),
          catchError(this.handleError<[]>('Error Updation In Meal', []))
        );
    }

    deleteMealFromCart(id:any,cartId:any): Observable<any> {
      return this.http
        .delete<[]>(BASIC_URL+`api/cart/${id}/${cartId}`,{
          headers: this.createAuthorizationHeader(),
        })
        .pipe(
          tap((_) => this.log('Meal Delete successfully')),
          catchError(this.handleError<[]>('Error Deleting Meal', []))
        );
    }

    createAuthorizationHeader(): HttpHeaders {
      let authHeaders: HttpHeaders = new HttpHeaders();
      return authHeaders.set(
        'Authorization',
        'Bearer ' + UserStorageService.getToken()
      );
    }
  
  
    log(message: string): void {
      console.log(`User Auth Service: ${message}`);
    }
  
    handleError<T>(operation = 'operation', result?: T): any {
      return (error: any): Observable<T> => {
  
        // TODO: send the error to remote logging infrastructure
        console.error(error); // log to console instead
  
        // TODO: better job of transforming error for user consumption
        this.log(`${operation} failed: ${error.message}`);
  
        // Let the app keep running by returning an empty result.
        return of(result as T);
      };
    }
}
