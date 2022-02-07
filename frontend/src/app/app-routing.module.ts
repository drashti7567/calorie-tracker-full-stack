import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NavigationConstants } from './shared/constants/navigation-constants';

const routes: Routes = [
  {path: '',   redirectTo: `/${NavigationConstants.HOME}`, pathMatch: 'full'},

  {path: NavigationConstants.HOME, 
  loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule)  
},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
