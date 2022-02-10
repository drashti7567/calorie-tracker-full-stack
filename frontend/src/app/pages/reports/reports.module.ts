import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReportsComponent } from './reports.component';
import { ReportsRoutingModule } from './reports.routing';
import { ReportsService } from './reports.service';

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
    ReportsService
  ],
  exports: [
  ]
})
export class ReportsModule { }


