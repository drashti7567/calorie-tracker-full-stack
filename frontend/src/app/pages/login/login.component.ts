import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';

import { NgxPermissionsService } from 'ngx-permissions';

import { InstantErrorStateMatcher } from '../../shared/components/error-state-matcher/error-state-matcher';
import { AuthenticationService } from '../../shared/services/authentication.service';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'ctf-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    public loginFormGroup;
    public hidePassword = true;
    public disableLoginButton = true;
    public loading = false;
    private returnUrl;

    public errorStateMatcher = new InstantErrorStateMatcher();

    constructor(private formBuilder: FormBuilder,
        // public toastService: ToastrService,
        private route: ActivatedRoute,
        private router: Router,
        private permisisonsService: NgxPermissionsService,
        private authenticationService: AuthenticationService,
        private toastService: ToastrService) {

        this.loginFormGroup = this.formBuilder.group({
            email: ["", [Validators.required, Validators.email]],
            password: ["", [Validators.required]]
        });

    }
    
    public ngOnInit(): void {
        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
        this.subscribeToLoginFormGroupChanges();
    }

    private subscribeToLoginFormGroupChanges(): void {
        this.loginFormGroup.valueChanges.subscribe(data => {
            this.disableLoginButton =
                !this.loginFormGroup.get("email").valid ||
                !this.loginFormGroup.get("password").valid;
        });
    }

    public onLoginButtonClick(): void {
        this.loading = true;
        this.authenticationService.login(
            this.loginFormGroup.get("email").value, this.loginFormGroup.get("password").value)
            .subscribe(
                data => {
                    if (!!data.success) {
                        this.toastService.success('Welcome ' + data.username);
                        const userPermission = JSON.parse(localStorage.getItem('currentUser')).roles[0];
                        this.router.navigate([this.returnUrl]);
                        this.permisisonsService.addPermission(userPermission);
                    } else {
                        this.toastService.error(data.message);
                        this.loading = false;
                    }
                },
                error => {
                    this.toastService.error("Invalid Credentials");
                    this.loading = false;
                });
    }
}