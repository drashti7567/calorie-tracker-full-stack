import { Component, NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
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
export class MockAddEntryModalComponent { }

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
      declarations: [HomeComponent, MockAddEntryModalComponent],
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
    expect(component).toBeTruthy();
  });

  it('addNewFoodEntry Method is called on click of add-entry-btn-div', () => {
    spyOn(component, 'addNewFoodEntry');
    fixture.detectChanges();

    let button = fixture.debugElement.query(By.css('.add-entry-btn-div')).nativeElement;
    button.click();

    fixture.whenStable().then(() => {

      expect(component.addNewFoodEntry).toHaveBeenCalled();
    })
  });
});
