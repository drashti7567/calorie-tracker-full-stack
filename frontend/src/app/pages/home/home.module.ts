import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HomeComponent } from './home.component';
import { HomeRoutingModule } from './home.routing';

@NgModule({
  imports: [
    HomeRoutingModule,
    CommonModule,
    SharedModule
  ],
  declarations: [
    HomeComponent
  ],
  providers: [
  ],
  exports: [
  ]
})
export class HomeModule { }
