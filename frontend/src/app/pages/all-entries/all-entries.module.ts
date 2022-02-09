import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AllEntriesComponent } from './all-entries.component';
import { AllEntriesRoutingModule } from './all-entries.routing';

@NgModule({
  imports: [
    AllEntriesRoutingModule,
    CommonModule,
    SharedModule
  ],
  declarations: [
    AllEntriesComponent
  ],
  providers: [
  ],
  exports: [
  ]
})
export class AllEntriesModule { }


