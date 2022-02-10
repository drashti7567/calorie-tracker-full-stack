import { Component, NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbAccordionModule, NgbDatepickerModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'app/shared/shared.module';
import { ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';

import { HomeComponent } from './home.component';
import { HomeService } from './home.service';

@Component({
  selector: 'cc-add-entry-modal',
  template: '',
  styles: []
})
export class MockAddEntryModalComponent {}

export class MockHomeService {
  getFoodEntries(x1, x2, x3) {
    return of({});
  } 
}

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  let homeServiceSpy = jasmine.createSpyObj('HomeService', ["getFoodEntries", "addFoodEntry"]);
  // let modalServiceSpy = jasmine.createSpyObj('modalService', ["open"]);
  let toastrServiceSpy = jasmine.createSpyObj('ToastrService', ["success", "error"]);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [NgbDatepickerModule, NgbAccordionModule],
      declarations: [ HomeComponent, MockAddEntryModalComponent ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        {
          provide: HomeService,
          useClass: MockHomeService
        },
        {
          provide: ToastrService,
          useValue: toastrServiceSpy
        }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    component.currentUser = {
      id: "Test_ID"
    };
    localStorage.setItem("currentUser", JSON.stringify(component.currentUser));
    fixture.detectChanges();
  });

  it('should create', () => {
    // let homeService = fixture.debugElement.injector.get(HomeService);
    // (Object.getOwnPropertyDescriptor(homeServiceSpy, "getFoodEntries")?.get as jasmine.Spy<() => any>).and.returnValue(of({}));
    // spyOnProperty(homeServiceSpy, "getFoodEntries").and.returnValue(of({}));
    expect(component).toBeTruthy();
  });
});
