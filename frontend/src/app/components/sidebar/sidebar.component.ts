import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from 'app/app.state';
import { NavigationConstants } from '../../shared/constants/navigation-constants';
import { AuthenticationService } from '../../shared/services/authentication.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ToggleSidebarService } from 'app/shared/services/toggle-sidebar.service';
import { CalorieLimitService } from 'app/shared/services/calorie-limit.service';

@Component({
    selector: 'cc-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

    constructor(public authenticationService: AuthenticationService, 
        private toggleSidebarService: ToggleSidebarService,
        private calorieService: CalorieLimitService) { }

    public navigationUrls = NavigationConstants;
    private destroy$: Subject<boolean> = new Subject<boolean>();

    public currentUser;
    public userCalorieLimit;

    private getCalorieLimit(): void {
        /**
         * FUnction to get calorie limit of user from backend
         */
        this.calorieService.getCalorieLimit(this.currentUser.id).pipe(takeUntil(this.destroy$))
        .subscribe(data => {
            if(!!data.success) this.userCalorieLimit = data.calories; 
        });
    }

    ngOnInit() {
        this.currentUser = JSON.parse(localStorage.getItem("currentUser"));
        this.getCalorieLimit();
    }

    toggleSidebar() {
        /**
         * FUnction to toggle sidebar when clicked on button
         */
        this.toggleSidebarService.toggleSidebar();
      }

}
