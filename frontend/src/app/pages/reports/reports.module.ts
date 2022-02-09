import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReportsComponent } from './reports.component';
import { ReportsRoutingModule } from './reports.routing';

@NgModule({
  imports: [
    ReportsRoutingModule,
    CommonModule,
    SharedModule
  ],
  declarations: [
    ReportsComponent
  ],
  providers: [
  ],
  exports: [
  ]
})
export class ReportsModule { }


