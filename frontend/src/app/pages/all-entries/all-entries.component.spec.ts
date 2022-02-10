import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbAccordionModule, NgbDatepickerModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';

import { AllEntriesComponent } from './all-entries.component';
import { AllEntriesService } from './all-entries.service';

export class MockAddEntryModalComponent {}

export class MockAllEntriesService {
  getAllFoodEntries(x1, x2, x3) {
    return of({});
  } 

  getFoodEntries(x1, x2, x3) {
    return of({});
  } 
}



describe('AllEntriesComponent', () => {
  let component: AllEntriesComponent;
  let fixture: ComponentFixture<AllEntriesComponent>;

  let allEntriesServiceSpy = jasmine.createSpyObj('AllEntriesService', ["getFoodEntries", "addFoodEntry", "deleteFoodEntry", "updateFoodEntry"]);
  let ToastServiceSpy = jasmine.createSpyObj('ToastrService', ["success", "error"]);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [NgbDatepickerModule, NgbAccordionModule],
      declarations: [ AllEntriesComponent ],
      providers: [{
        provide: AllEntriesService,
        useClass: MockAllEntriesService
      },
      {
        provide: ToastrService,
        useValue: ToastServiceSpy
      }],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllEntriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
