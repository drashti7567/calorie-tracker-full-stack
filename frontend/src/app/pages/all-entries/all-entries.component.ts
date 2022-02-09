import { Component, OnInit } from '@angular/core';
import { NgbCalendar, NgbDate, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { ModalService } from 'app/shared/components/modal/modal.service';
import { FoodEntryRequest } from 'app/shared/models/request/foodEntry/food-entry-request.model';
import { FoodEntryFields } from 'app/shared/models/response/foodEntry/get-food-entries-response.model';
import moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AllEntriesService } from './all-entries.service';


@Component({
  selector: 'cc-all-entries',
  templateUrl: './all-entries.component.html',
  styleUrls: ['./all-entries.component.scss']
})
export class AllEntriesComponent {

  constructor(private calendar: NgbCalendar, public formatter: NgbDateParserFormatter,
    private allEntriesService: AllEntriesService, private modalService: ModalService,
    private toastService: ToastrService) { }

  public currentUser;

  private destroy$: Subject<boolean> = new Subject<boolean>();

  public toDate;
  public fromDate;

  public todayDate = this.calendar.getToday();

  public maxDate = { year: this.todayDate.year, month: this.todayDate.month, day: this.todayDate.day }
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
  public index = "index";
  public object = Object;

  public selectedEntry: FoodEntryFields;

  // Datepicker functions ------------------------------------------------------------------------

  private filterFoodEntries(): void {
    /**
     * FUnction to filter the foodEntrylist based on the dat entered in the date pickers.
     */



    this.foodEntriesList = this.foodEntriesList.filter(entry => {
      const eatDate: NgbDate = NgbDate.from({
        year: Number.parseInt(entry.eatingTime.substring(0, 4)),
        month: Number.parseInt(entry.eatingTime.substring(5, 7)),
        day: Number.parseInt(entry.eatingTime.substring(8, 10))
      });
      return (eatDate.after(this.fromDate) || eatDate.equals(this.fromDate)) &&
        (this.toDate.after(eatDate) || eatDate.equals(this.toDate));
    });
    this.mapEntriesAccordingToDate();
  }

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date &&
      (date.after(this.fromDate) || date.equals(this.fromDate))) {
      this.toDate = date;
      this.filterFoodEntries();
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

  // -----------------------------------------------------------------------------------------------

  private mapEntriesAccordingToDate(): void {
    /**
     * Function that given list of food entries - clubs same date entries together 
     * and then populates them in a list.
     */
    this.dateWiseMap = {};
    this.totalCalorieDayWiseMap = {};
    this.foodEntriesList.forEach(entry => {
      const entryDate = entry.eatingTime.substring(0, 10);
      if (!(entryDate in this.dateWiseMap)) {
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
    this.allEntriesService.getFoodEntries().pipe(takeUntil(this.destroy$)).subscribe(data => {
      if (!!data.success) {
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
     * FUnction to open the add entry modal.
     */
    this.modalService.open("add-entry-modal");

  }

  public openDeleteEntryModal(entry): void {
    /**
     * FUnction to open the delete entry modal.
     */
    this.selectedEntry = entry;
    this.modalService.open("delete-entry-modal");

  }

  public updateDeleteEntryModal(): void {
    /**
     * FUnction to open the add entry modal.
     */
    this.modalService.open("update-entry-modal");

  }

  public addNewFoodEntry(request: FoodEntryRequest): void {
    /**
     * Function to send post request to add new entry
     */
    this.allEntriesService.addFoodEntry(request).pipe(takeUntil(this.destroy$)).subscribe(data => {
      if (!!data.success) {
        this.toastService.success(data.message);
        this.getFoodEntries();
      }
      else this.toastService.error(data.message);
    });
  }

  public deleteFoodEntry(entryId: string): void {
    /**
     * Function to send post request to add new entry
     */
    this.allEntriesService.deleteFoodEntry(entryId, this.currentUser.id)
      .pipe(takeUntil(this.destroy$)).subscribe(data => {
        if (!!data.success) {
          this.toastService.success(data.message);
          this.getFoodEntries();
        }
        else this.toastService.error(data.message);
      });
  }

  public updateFoodEntry(entryId: string, request: FoodEntryRequest): void {
    /**
     * Function to send post request to add new entry
     */
    this.allEntriesService.updateFoodEntry(entryId, this.currentUser.id)
      .pipe(takeUntil(this.destroy$)).subscribe(data => {
        if (!!data.success) {
          this.toastService.success(data.message);
          this.getFoodEntries();
        }
        else this.toastService.error(data.message);
      });
  }
}
