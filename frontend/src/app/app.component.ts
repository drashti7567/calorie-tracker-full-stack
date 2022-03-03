import { Component } from '@angular/core';
import { NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router, RouterEvent } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ToggleSidebarService } from './shared/services/toggle-sidebar.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = 'pro-dashboard-angular';
  
  // Sets initial value to true to show loading spinner on first load
  public loading = true;

  constructor(private appService: ToggleSidebarService, private router: Router) {
    this.router.events.subscribe((e : RouterEvent) => {
      this.navigationInterceptor(e);
    })
  }

  // Shows and hides the loading spinner during RouterEvent changes
  navigationInterceptor(event: RouterEvent): void {
    if (event instanceof NavigationStart) {
      this.loading = true;
    }
    if (event instanceof NavigationEnd) {
      this.loading = false;
    }

    // Set loading state to false in both of the below events to hide the spinner in case a request fails
    if (event instanceof NavigationCancel) {
      this.loading = false;
    }
    if (event instanceof NavigationError) {
      this.loading = false;
    }
  }
}
