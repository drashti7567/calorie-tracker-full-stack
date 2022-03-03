import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { StoreModule } from '@ngrx/store';

import { CollapseModule } from 'ngx-bootstrap/collapse';
import { ToastrModule } from 'ngx-toastr';
import { NgxPermissionsModule, NgxPermissionsService } from 'ngx-permissions';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { FooterComponent } from './components/footer/footer.component';
import { SharedModule } from './shared/shared.module';
import { AuthenticationService } from './shared/services/authentication.service';
import { ModalService } from './shared/components/modal/modal.service';
import { JwtInterceptor } from './shared/interceptors/jwt.interceptor';
import { ErrorInterceptor } from './shared/interceptors/error.interceptor';
import { LayoutComponent } from './layout/layout.component';

export function appInitializerFn(ps: NgxPermissionsService,
    authenticationService: AuthenticationService) {
    if (!!localStorage.getItem('currentUser')) {
      return () => {
        authenticationService.refreshToken();
        ps.addPermission(JSON.parse(localStorage.getItem('currentUser')).roles[0]);
      }
    }
    else return () => { };
  };

@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        CommonModule,
        HttpClientModule,
        BrowserAnimationsModule,
        CollapseModule.forRoot(),
        ToastrModule.forRoot(),
        StoreModule.forRoot({
        }),

    NgxPermissionsModule.forRoot(),
        SharedModule
    ],
    declarations: [
        AppComponent,
        NavbarComponent,
        SidebarComponent,
        FooterComponent,
        LayoutComponent
    ],
    providers: [
    ModalService,
    {
      provide: APP_INITIALIZER,
      useFactory: appInitializerFn,
      deps: [NgxPermissionsService, AuthenticationService],
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    },
    ],
    entryComponents: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
