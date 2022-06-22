import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, of, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserStorageService } from '../storage/user-storage.service';

const BASIC_URL = environment['BASIC_URL'];
export const AUTH_HEADER = 'authorization';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  
  constructor(private http: HttpClient,
    private userStorageService : UserStorageService) { }

    login(username: string, password: string): any {
      return this.http.post<[]>( BASIC_URL + 'authenticate', {
        username,
        password
      }, {observe: 'response'})
        .pipe(
          tap(_ => this.log('User Authentication')),
          map((res: HttpResponse<any>) => {
            console.log("res is ervice",res);
            this.userStorageService.saveUser(res.body);
            console.log(res);
            const tokenLength = res.headers.get(AUTH_HEADER).length;
            const bearerToken = res.headers.get(AUTH_HEADER).substring(7, tokenLength);
            this.userStorageService.saveToken(bearerToken);
            return res;
        }));
    }

  
  register(data):Observable<any>{
    console.log(data);
    return this.http.post(BASIC_URL+"sign-up",data);
  }

  getUserById(): Observable<any> {
    const userId = UserStorageService.getUserId();
    return this.http
      .get<[]>(BASIC_URL+`user/${userId}`, {
        headers: this.createAuthorizationHeader(),
      })
      .pipe(
        tap((_) => this.log('User Fetched successfully')),
        catchError(this.handleError<[]>('Error Fetching User', []))
      );
  }

  changePassword(data): Observable<any> {
    data.userId = UserStorageService.getUserId();
    return this.http
      .post<[]>(BASIC_URL+`user/changePassword`,data, {
        headers: this.createAuthorizationHeader(),
      })
      .pipe(
        tap((_) => this.log('Password Updated successfully')),
        catchError(this.handleError<[]>('Error Updating Password', []))
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