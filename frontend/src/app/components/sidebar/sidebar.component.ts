import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from 'app/app.state';
import { NavigationConstants } from 'app/shared/constants/navigation-constants';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

    constructor(private store: Store) { }

    public navigationUrls = NavigationConstants;
    private destroy$: Subject<boolean> = new Subject<boolean>();

    ngOnInit() {
    }

}
