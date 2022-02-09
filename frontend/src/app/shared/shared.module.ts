import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts';
import { ModalModule } from './components/modal/modal.module';
import { ModalService } from './components/modal/modal.service';
import { AuthGuard } from './services/auth-guard.service';
import { AuthenticationService } from './services/authentication.service';
import { LoggedInAuthGuard } from './services/logged-in-auth-guard.service';
import { ToggleSidebarService } from './services/toggle-sidebar.service';

@NgModule({
  imports: [
    CommonModule,
    ChartsModule,
    ModalModule
  ],
  declarations: [
  ],
  providers: [
    ToggleSidebarService,
    ModalService,
    AuthenticationService,
    LoggedInAuthGuard,
    AuthGuard
  ],
  exports: [
    ChartsModule,
  ]
})
export class SharedModule { }
