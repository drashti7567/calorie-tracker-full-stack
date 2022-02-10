import { Component, OnDestroy, OnInit } from '@angular/core';
import { GetReportsResponse } from 'app/shared/models/response/reports/get-reports-response.model';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ReportsService } from './reports.service';

import { Label } from 'ng2-charts';
import { ChartDataSets, ChartOptions, ChartType, TickOptions } from 'chart.js';
import moment, { Moment } from 'moment';


@Component({
  selector: 'cc-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit, OnDestroy {

  constructor(private reportsService: ReportsService, private toastService: ToastrService) { }

  public reportsData: GetReportsResponse;

  public chartColors = [
    {
      backgroundColor: ["#242939", "#36a2eb", "#98a4c7", "#c4cff9", "#5b6582", "#242939",]
    }
  ];
  public entriesLineChartData;
  public entriesLineChartLabels;
  public entriesLineChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      xAxes: [{
        scaleLabel: {
          display: true,
          labelString: "Days"
        },
      }]
    },
    tooltips: {}
  };
  public entriesLineChartLegend = false;
  public entriesLineChartPlugins
  public entriesLineChartType: ChartType = 'line';

  public averageCaloriesUserChartData;
  public averageCaloriesUserChartLabels;
  public averageCaloriesUserChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [{
        scaleLabel: {
          display: true,
          labelString: "Average Calories"
        },
      }]
    },
    tooltips: {}
  };
  public averageCaloriesUserChartLegend = false;
  public averageCaloriesUserChartPlugins;
  public averageCaloriesUserChartType: ChartType = 'bar';

  private destroy$: Subject<boolean> = new Subject<boolean>();

  // -----------------------------------------------------------------------------------------------

  private getDates(startDate: Moment, stopDate: Moment): Moment[] {
    /**
     * Function to return dates between from and to date.
     */
    var dateArray = new Array();
    var currentDate = startDate;
    while (currentDate <= stopDate) {
      dateArray.push(currentDate.format("YYYY-MM-DD"));
      currentDate = currentDate.add(1, 'd');
    }
    return dateArray;
  }

  private getEntriesLineChartData(): void {
    /**
     * Populate Line Chart Variables for current week entries vs past week entries.
     */

    const weekDays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    const currentWeekDates = this.getDates(moment().subtract(6, 'd'), moment())
    const pastWeekDates = this.getDates(moment().subtract(13, 'd'), moment().subtract(7, 'd'))

    const today = moment.now();
    const day = weekDays[moment(today).day()];

    const indexOfWeekDay = weekDays.indexOf(day);
    const labels = weekDays.slice((indexOfWeekDay + 1) % 7);
    if (labels.length != 7) labels.push(...weekDays.slice(0, indexOfWeekDay + 1));
    this.entriesLineChartLabels = labels;

    this.entriesLineChartData = [
      {
        data: [],
        label: "Current Week"
      },
      {
        data: [],
        label: "Past Week"
      }
    ];

    for (let i = 0; i < 7; i++) {
      let index = this.reportsData.currentWeekEntriesList.findIndex(entry => entry.day === labels[i]);
      this.entriesLineChartData[0].data.push(index != -1 ? this.reportsData.currentWeekEntriesList[index].totalEntries : 0)
      index = this.reportsData.pastWeekEntriesList.findIndex(entry => entry.day === labels[i]);
      this.entriesLineChartData[1].data.push(index != -1 ? this.reportsData.pastWeekEntriesList[index].totalEntries : 0)
    }

    this.entriesLineChartOptions["tooltips"] = {
      callbacks: {
        label: (context, data) => {
          const wLabel = labels.indexOf(context.label)
          return (context.datasetIndex == 0 ? currentWeekDates[wLabel] : pastWeekDates[wLabel]) + ": " + context.value;
        }
      }
    }

  }

  private getAverageCaloriesPerUserChartData(): void {
    /**
     * Function to get average calories added per user graph data
     */
    this.averageCaloriesUserChartLabels = this.reportsData.averageCaloriesList.map(entry => entry.userName);
    this.averageCaloriesUserChartData = [{
      data: [...this.reportsData.averageCaloriesList.map(entry => entry.calories)],
      label: "Calories"
    }];
  }

  private getReports(): void {
    /**
     * Function to get the reports data from backend
     */
    this.reportsService.getReports().pipe(takeUntil(this.destroy$)).subscribe(data => {
      if (!!data.success) {
        console.log(data);
        this.reportsData = data;
        this.getEntriesLineChartData();
        this.getAverageCaloriesPerUserChartData();
      }
      else {
        this.toastService.error(data.message);
      }
    })
  }

  ngOnInit(): void {
    this.getReports();
  }

  // -----------------------------------------------------------------------------------------------

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.complete();
  }

}
