import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllEntriesComponent } from './all-entries.component';

const routes: Routes = [
  {
    path: '',
    component: AllEntriesComponent,
  },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [],
})
export class AllEntriesRoutingModule { }
