import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from 'app/app.state';
import { NavigationConstants } from '../../shared/constants/navigation-constants';
import { AuthenticationService } from '../../shared/services/authentication.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ToggleSidebarService } from 'app/shared/services/toggle-sidebar.service';

@Component({
    selector: 'cc-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

    constructor(public authenticationService: AuthenticationService, 
        private toggleSidebarService: ToggleSidebarService) { }

    public navigationUrls = NavigationConstants;
    private destroy$: Subject<boolean> = new Subject<boolean>();

    public currentUser;

    ngOnInit() {
        this.currentUser = JSON.parse(localStorage.getItem("currentUser"));

    }

    toggleSidebar() {
        this.toggleSidebarService.toggleSidebar();
      }

}
