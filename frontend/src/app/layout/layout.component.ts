import { Component } from '@angular/core';
import { ToggleSidebarService } from 'app/shared/services/toggle-sidebar.service';

declare const $: any;

@Component({
  selector: 'cc-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {

  constructor(private toggleSidebarService: ToggleSidebarService) {}

  getClasses() {
    const classes = {
      'pinned-sidebar': this.toggleSidebarService.getSidebarStat().isSidebarPinned,
      'toggeled-sidebar': this.toggleSidebarService.getSidebarStat().isSidebarToggeled
    }
    return classes;
  }
  
  toggleSidebar() {
    this.toggleSidebarService.toggleSidebar();
  }
}