import { ToggleSidebarService } from '../../shared/services/toggle-sidebar.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'cc-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private toggleSidebarService: ToggleSidebarService) { }
  isCollapsed = true;
  ngOnInit() {
  }

  toggleSidebarPin() {
    this.toggleSidebarService.toggleSidebarPin();
  }
  toggleSidebar() {
    this.toggleSidebarService.toggleSidebar();
  }

}
