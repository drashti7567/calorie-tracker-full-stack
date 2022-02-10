import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';

import { ReportsComponent } from './reports.component';
import { ReportsService } from './reports.service';

export class MockReportsService {
  getReports(x1, x2, x3) {
    return of({});
  } 

  getFoodEntries(x1, x2, x3) {
    return of({});
  } 
}

describe('ReportsComponent', () => {
  let component: ReportsComponent;
  let fixture: ComponentFixture<ReportsComponent>;

  let reportsServiceSpy = jasmine.createSpyObj('reportsService', ["getReports"]);
  let toastrServiceSpy = jasmine.createSpyObj('toastrService', ["success", "error"]);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportsComponent ],
      providers: [{
        provide: ReportsService,
        useClass: MockReportsService
      },
      {
        provide: ToastrService,
        useValue: toastrServiceSpy
      }]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
