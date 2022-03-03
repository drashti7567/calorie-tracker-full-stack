import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AllEntriesComponent } from './all-entries.component';
import { AllEntriesRoutingModule } from './all-entries.routing';
import { AllEntriesService } from './all-entries.service';

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
    AllEntriesService
  ],
  exports: [
  ]
})
export class AllEntriesModule { }


