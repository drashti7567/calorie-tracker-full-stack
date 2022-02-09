import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoginRoutingModule } from './login.routing';
import { LoginComponent } from './login.component';
import { AuthenticationService } from '../../shared/services/authentication.service';
import { NgxPermissionsModule } from 'ngx-permissions';


@NgModule({
    imports: [
        SharedModule,
        NgxPermissionsModule.forChild(),
        LoginRoutingModule,
    ],
    declarations: [
        LoginComponent
    ],
    providers: [
        AuthenticationService,
    ]
})
export class LoginModule { }
