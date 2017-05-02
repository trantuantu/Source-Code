import {Component, OnInit} from '@angular/core';
import {User} from "../_models/user";
import {AngularFire, AuthProviders} from 'angularfire2';
import {Router} from "@angular/router";
import {UserService} from "../_services/user.service";
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [UserService]
})
export class LoginComponent implements OnInit {

  user: User;
  isLogin: boolean;

  constructor(private userService: UserService,
              private router: Router,
              public af: AngularFire) {
    this.user = new User();
  }

  ngOnInit() {
    this.af.auth.subscribe(user => {
      if (user) {
        this.user.uid = user.facebook.uid;
        this.user.displayName = user.facebook.displayName;
        this.user.email = user.facebook.email;
        this.user.photoURL = user.facebook.photoURL;
        this.user.refreshToken = user.auth.refreshToken;
        this.userService.login(this.user.uid);
        this.isLogin = true;
      }
      else {
        this.isLogin = false;
      }
    });
  }

  loginFacebook() {
    this.af.auth.login({
      provider: AuthProviders.Facebook
    });
  }

  logout() {
    this.af.auth.logout();
    this.router.navigate(['']); // Go to Home page
  }
}
