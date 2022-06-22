import { Injectable } from '@angular/core';


const TOKEN = 'lkd_token';
const USER = 'lkd_user';

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {

  constructor() { }

  public saveToken(token: string): void {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN, token);
  }

  static hasToken(): boolean {
    if ( this.getToken() === null){
      return false;
    }
    return true;
  }

  static getToken(): string {
    return localStorage.getItem(TOKEN);
  }

  public saveUser(user): void {
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  static getUser(): any {
    return JSON.parse(localStorage.getItem(USER));
  }


  static getUserId(): string {
    const user = this.getUser();
    if ( user == null){ return ''; }
    return user.userId;
  }

  static getUserRole(): string {
    const user = this.getUser();
    if ( user == null){ return ''; }
    return user.role;
  }

  static isAdminLoggedIn(): boolean {
    if ( this.getToken() === null){
      return false;
    }
    const role: string = this.getUserRole();
    return role == 'ADMIN';
  }

  static isUserLoggedIn(): boolean {
    if ( this.getToken() === null){
      return false;
    }
    const role: string = this.getUserRole();
    return role == 'USER';
  }



  static signOut(): void {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }

  
}