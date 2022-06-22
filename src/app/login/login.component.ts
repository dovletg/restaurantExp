import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { AuthService } from '../services/auth/auth.service';
import { UserStorageService } from '../services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

 
  validateForm!: FormGroup;
  isSpinning = false;

  submitForm(): void {
    this.isSpinning = true;
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    this.authService.login(this.validateForm.get(['userName'])!.value,this.validateForm.get(['password'])!.value).subscribe(res=>{
     this.isSpinning = false;
     if(UserStorageService.isAdminLoggedIn()){
      this.router.navigateByUrl('admin/dashboard');
     }else if(UserStorageService.isUserLoggedIn()){
      this.router.navigateByUrl('user/dashboard');
     }
   
     console.log("res",res);
    },error=>{
      console.log("errorr",error);
      if(error.status == 406){
        this.notification
        .error(
          'ERROR',
          `User Is Not Active. Please Verify Email.`,
          { nzDuration: 5000 }
        )
      }else{
        this.notification
        .error(
          'ERROR',
          `Bad credentials`,
          { nzDuration: 5000 }
        )
      }
      this.isSpinning = false;
    
    })

  }

  constructor(private fb: FormBuilder, 
              private authService:AuthService,
              private notification: NzNotificationService,
              private router: Router,) {}

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [true]
    });
  }




}