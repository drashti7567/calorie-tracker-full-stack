import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts';
import { MatIconModule } from '@angular/material/icon';
import { ModalModule } from './components/modal/modal.module';
import { ModalService } from './components/modal/modal.service';
import { AuthGuard } from './services/auth-guard.service';
import { AuthenticationService } from './services/authentication.service';
import { LoggedInAuthGuard } from './services/logged-in-auth-guard.service';
import { ToggleSidebarService } from './services/toggle-sidebar.service';
import { MaterialModule } from './modules/material.module';

@NgModule({
  imports: [
    CommonModule,
    ChartsModule,
    ModalModule,
    MaterialModule
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
    MaterialModule
  ]
})
export class SharedModule { }
