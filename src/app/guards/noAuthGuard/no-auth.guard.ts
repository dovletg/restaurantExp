import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';

@Injectable({
  providedIn: 'root'
})
export class NoAuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {

    if(UserStorageService.hasToken() && UserStorageService.isUserLoggedIn()){
      this.router.navigateByUrl('/user/dashboard');
      return false;
    }
    else if (UserStorageService.hasToken() && UserStorageService.isAdminLoggedIn()){
      this.router.navigateByUrl('/admin/dashboard');
      return false;
    }
    return true;
  }
  
  
}