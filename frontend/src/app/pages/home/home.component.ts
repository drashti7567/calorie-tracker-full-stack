import { Component, OnInit } from '@angular/core';
import { NgbCalendar, NgbDate, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import moment from 'moment';


@Component({
  selector: 'cc-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private calendar: NgbCalendar, public formatter: NgbDateParserFormatter) {}

  public currentUser;

  public toDate: NgbDate = this.calendar.getToday();
  public fromDate: NgbDate = new NgbDate(this.toDate.year, this.toDate.month, this.toDate.day);
  
  public maxDate = {year: this.toDate.year, month: this.toDate.month, day: this.toDate.day}
  public minDate = moment("01/01/2010", "DD/MM/YYYY").toDate();

  public displayMonths = 2;
  public navigation = 'select';
  public showWeekNumbers = false;
  public outsideDays = 'visible';
  public hoveredDate;


  // Datepicker functions ------------------------------------------------------------------------

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date && date.after(this.fromDate)) {
      this.toDate = date;
      // this.gridApi.purgeInfiniteCache();
    } else {
      this.toDate = null;
      this.fromDate = date;
    }
  }

  isHovered(date: NgbDate) {
    return this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate);
  }

  isInside(date: NgbDate) {
    return this.toDate && date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return date.equals(this.fromDate) || (this.toDate && date.equals(this.toDate)) || this.isInside(date) || this.isHovered(date);
  }

  validateInput(currentValue: NgbDate | null, input: string): NgbDate | null {
    const parsed = this.formatter.parse(input);
    return parsed && this.calendar.isValid(NgbDate.from(parsed)) ? NgbDate.from(parsed) : currentValue;
  }

  // -----------------------------------------------------------------------------------------------

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem("currentUser"));
    console.log(this.currentUser);
  }
}
