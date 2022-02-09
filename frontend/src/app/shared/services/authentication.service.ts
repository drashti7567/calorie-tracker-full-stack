import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { NgxPermissionsService } from 'ngx-permissions';
import { UrlConstants } from '../constants/url-constants';

@Injectable()
export class AuthenticationService {
    constructor(private http: HttpClient,
        private route: Router,
        // private toastService: ToastrService,
        private permissionsService: NgxPermissionsService) { }

    private authUrl = UrlConstants.API_URL + UrlConstants.AUTH_URL;
    private signInUrl = this.authUrl + UrlConstants.SIGN_IN_URL;
    private refreshTokenUrl = this.authUrl + UrlConstants.REFRESH_TOKEN_URL;

    public login(email: string, password: string): Observable<any> {
        return this.http.post<any>(this.signInUrl, { email: email, password: password })
            .map(user => {
                // login successful if there's a jwt token in the response
                if (user && user.token) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    this.refreshToken();
                }
                return user;
            });
    }

    public refreshToken() {
        const currentUser = JSON.parse(localStorage.getItem("currentUser"));
            let resetTokenSubscription = this.http.get(this.refreshTokenUrl).subscribe((response: any) => {
                if (!!response.success) {
                    currentUser.token = response.newToken;
                    localStorage.setItem('currentUser', JSON.stringify(currentUser));
                    resetTokenSubscription.unsubscribe();

                    const jwtToken = JSON.parse(atob(response.newToken.split(".")[1]));

                    const expires = new Date(jwtToken.exp * 1000);
                    const timeout = expires.getTime() - Date.now() - (60 * 1000);
                    setTimeout(() => this.refreshToken(), timeout);

                } else {
                    // this.toastService.error("Your session has expired. Please log in again");
                    this.logout();
                }
            });
    }

    public logout(): void {
        // remove user from local storage to log user out and 
        // flush all the permissions they have got
        localStorage.removeItem('currentUser');
        this.permissionsService.flushPermissions();
        this.route.navigate(["/login"]);
    }
}