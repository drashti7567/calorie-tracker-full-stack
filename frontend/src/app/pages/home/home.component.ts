import { Component, OnInit } from '@angular/core';
import { NgbCalendar, NgbDate, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { ModalService } from 'app/shared/components/modal/modal.service';
import { FoodEntryRequest } from 'app/shared/models/request/foodEntry/food-entry-request.model';
import { FoodEntryFields } from 'app/shared/models/response/foodEntry/get-food-entries-response.model';
import { GetReportsResponse } from 'app/shared/models/response/reports/get-reports-response.model';
import moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { take } from 'rxjs-compat/operator/take';
import { takeUntil } from 'rxjs/operators';
import { HomeService } from './home.service';


@Component({
  selector: 'cc-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private calendar: NgbCalendar, public formatter: NgbDateParserFormatter,
    private homeService: HomeService, private modalService: ModalService,
    private toastService: ToastrService) { }

  public currentUser;

  private destroy$: Subject<boolean> = new Subject<boolean>();

  public toDate: NgbDate = this.calendar.getToday();
  public fromDate: NgbDate = new NgbDate(this.toDate.year, this.toDate.month, this.toDate.day);

  public maxDate = { year: this.toDate.year, month: this.toDate.month, day: this.toDate.day }
  public minDate = moment("01/01/2010", "DD/MM/YYYY").toDate();

  public displayMonths = 2;
  public navigation = 'select';
  public showWeekNumbers = false;
  public outsideDays = 'visible';
  public hoveredDate;

  public foodEntriesList: FoodEntryFields[];
  public userCalorieLimit;

  public dateWiseMap;
  public totalCalorieDayWiseMap;
  public defaultActiveId;
  public object = Object;


  // Datepicker functions ------------------------------------------------------------------------

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date && date.after(this.fromDate)) {
      this.toDate = date;
      this.getFoodEntries();
    } else {
      this.toDate = null;
      this.fromDate = date;
    }
  }

  isHovered(date: NgbDate) {
    return this.fromDate && !this.toDate && this.hoveredDate &&
      date.after(this.fromDate) && date.before(this.hoveredDate);
  }

  isInside(date: NgbDate) {
    return this.toDate && date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return date.equals(this.fromDate) || (this.toDate && date.equals(this.toDate)) ||
      this.isInside(date) || this.isHovered(date);
  }

  validateInput(currentValue: NgbDate | null, input: string): NgbDate | null {
    const parsed = this.formatter.parse(input);
    return parsed && this.calendar.isValid(NgbDate.from(parsed)) ? NgbDate.from(parsed) : currentValue;
  }

  // -----------------------------------------------------------------------------------------------

  private mapEntriesAccordingToDate(): void {
    /**
     * Function that given list of food entries - clubs same date entries together 
     * and then populates them in a list.
     */
    this.dateWiseMap = {};
    this.totalCalorieDayWiseMap = {};
    this.foodEntriesList.forEach(entry => {
      const entryDate = entry.eatingTime.substring(0,10);
      if(!(entryDate in this.dateWiseMap)) {
        this.dateWiseMap[entryDate] = [];
        this.totalCalorieDayWiseMap[entryDate] = 0;
      }
      this.dateWiseMap[entryDate].push(entry);
      this.totalCalorieDayWiseMap[entryDate] += entry.calories;
    });
  }

  private getFoodEntries(): void {
    /**
     * Function that calls the backend service to get the food entries made by user.
     */
    const fromDate = !!this.fromDate ? `${String(this.fromDate.year).padStart(4, '0')}-${String(this.fromDate.month).padStart(2,'0')}-${String(this.fromDate.day).padStart(2, '0')}` : null;
    const toDate = !!this.toDate ?  `${String(this.toDate.year).padStart(4, '0')}-${String(this.toDate.month).padStart(2,'0')}-${String(this.toDate.day).padStart(2, '0')}` : null;
    this.homeService.getFoodEntries(this.currentUser.id, fromDate, toDate).pipe(takeUntil(this.destroy$)).subscribe(data => {
      if(!!data.success) {
        this.foodEntriesList = data.foodEntryList;
        this.userCalorieLimit = !!data.calorieLimit ? data.calorieLimit : 2100;
        console.log(this.foodEntriesList, this.userCalorieLimit);
        this.mapEntriesAccordingToDate();
      }
    })


  }

  // -----------------------------------------------------------------------------------------------

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem("currentUser"));
    this.getFoodEntries();
  }

  // Public functions called from UI start----------------------------------------------------------

  public openAddEntryModal(): void {
    /**
     * FUnction to open the iorder summary modal.
     */
    this.modalService.open("add-entry-modal");

  }

  public addNewFoodEntry(request: FoodEntryRequest): void {
    /**
     * Function to send post request to add new entry
     */
    this.homeService.addFoodEntry(request).pipe(takeUntil(this.destroy$)).subscribe(data => {
      if(!!data.success) {
        this.toastService.success(data.message);
        this.getFoodEntries();
      }
      else this.toastService.error(data.message);
    });
  }
}
