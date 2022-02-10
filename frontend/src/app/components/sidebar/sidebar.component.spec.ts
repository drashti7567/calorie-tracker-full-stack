import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthenticationService } from 'app/shared/services/authentication.service';
import { CalorieLimitService } from 'app/shared/services/calorie-limit.service';
import { ToggleSidebarService } from 'app/shared/services/toggle-sidebar.service';
import { of } from 'rxjs';

import { SidebarComponent } from './sidebar.component';

export class MockCalorieLimitService {
  getCalorieLimit(x1, x2, x3) {
    return of({});
  }
}

describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;
  let authenticationServiceSpy = jasmine.createSpyObj('authenticationService', ["getFoodEntries", "addFoodEntry"]);
  let toggleSidebarServiceSpy = jasmine.createSpyObj('toggleSidebarService', ["toggleSidebar"]);
  let calorieServiceSpy = jasmine.createSpyObj('calorieLimitService', ["getCalorieLimit",]);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SidebarComponent ],
      providers: [
        {
          provide: AuthenticationService,
          useValue: authenticationServiceSpy
        },
        {
          provide: CalorieLimitService,
          useClass: MockCalorieLimitService
        },
        {
          provide: ToggleSidebarService,
          useValue: toggleSidebarServiceSpy
        }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarComponent);
    component = fixture.componentInstance;
    localStorage.setItem("currentUser", JSON.stringify({id: "mockId", roles: ["Admin"]}));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
