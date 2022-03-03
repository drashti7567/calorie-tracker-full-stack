import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NgxPermissionsGuard } from 'ngx-permissions';
import { LayoutComponent } from './layout/layout.component';
import { NavigationConstants } from './shared/constants/navigation-constants';
import { AuthGuard } from './shared/services/auth-guard.service';
import { LoggedInAuthGuard } from './shared/services/logged-in-auth-guard.service';

const routes: Routes = [
  { path: '', redirectTo: `/${NavigationConstants.HOME}`, pathMatch: 'full' },

  {
    path: '', component: LayoutComponent,
    children: [
      {
        path: NavigationConstants.HOME,
        loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule),
        canActivate: [AuthGuard]
      },
      {
        path: NavigationConstants.ALL_ENTRIES,
        loadChildren: () => import('./pages/all-entries/all-entries.module').then(m => m.AllEntriesModule),
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: {
          permissions: {
            only: 'ADMIN'
          }
        }
      },
      {
        path: NavigationConstants.REPORTS,
        loadChildren: () => import('./pages/reports/reports.module').then(m => m.ReportsModule),
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: {
          permissions: {
            only: 'ADMIN'
          }
        }
      },
    ]
  },
  {
    path: NavigationConstants.LOGIN_ROUTE,
    loadChildren: () => import('./pages/login/login.module').then(m => m.LoginModule),
    canActivate: [LoggedInAuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
