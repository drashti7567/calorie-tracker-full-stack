import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts';
import { ToggleSidebarService } from './services/toggle-sidebar.service';

@NgModule({
  imports: [
    CommonModule,
    ChartsModule
  ],
  declarations: [
  ],
  providers: [
    ToggleSidebarService,
  ],
  exports: [
    ChartsModule,
  ]
})
export class SharedModule { }
