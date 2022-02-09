import { Component, OnInit, Input, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbCalendar, NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { FoodEntryRequest } from 'app/shared/models/request/foodEntry/food-entry-request.model';
import { ProductWithCalorieResponse } from 'app/shared/models/response/searchFood/product-with-calorie-response.model';
import { AutoCompleteService } from 'app/shared/services/autocomplete.service';
import { map } from 'jquery';
import { Observable, of, Subject } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, switchMap, takeUntil, tap } from 'rxjs/operators';

import { ModalService } from '../../components/modal/modal.service';

@Component({
    selector: 'cc-add-entry-modal',
    templateUrl: './add-entry-modal.component.html',
    styleUrls: ['./add-entry-modal.component.scss']
})
export class AddEntryModalComponent implements OnInit {

    @Output("onMainButtonClick") onMainButtonClick = new EventEmitter();

    @Input("isComingFromAdmin") isComingFromAdmin: boolean = false;

    constructor(private modalService: ModalService, private formBuilder: FormBuilder,
        private calendar: NgbCalendar, private autocompleteService: AutoCompleteService) { }

    private currentUser;
    public addEntryForm;
    public usersList = [];
    private destroy$: Subject<boolean> = new Subject<boolean>();

    public loading = false;

    public toDate: NgbDate = this.calendar.getToday();
    public maxDate = { year: this.toDate.year, month: this.toDate.month, day: this.toDate.day }

    public disableSaveButton = true;

    // Typeahead functions ---------------------------------------------------------------------

    public searchFood = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            tap(data => this.loading = true),
            switchMap((searchText) => searchText.length >= 2 ? this.autocompleteService.searchFood(searchText)
                .pipe(switchMap(data => of(data.productList)), tap(data => this.loading = false)) : of([])),
            tap(data => this.loading = false)
        );

    public resultFormatter = (value: ProductWithCalorieResponse) => value.foodName;
    public inputFormatter = (value: any) => {
        if (value.foodName) return value.foodName;
        return value;
    }

    // ---------------------------------------------------------------------------------------------

    private getUsersList(): void {
        /**
         * Function to get List of users for user select
         */
        if (!this.isComingFromAdmin) return;
        this.autocompleteService.getUsers().pipe(takeUntil(this.destroy$)).subscribe(data => {
            if (!!data.success) {
                this.usersList = data.usersList;
            }
        });
    }

    private subscribeToFormValueChanges(): void {
        /**
         * FUnction that disbaled the save button whenever there is a change in inputs 
         * and inputs are invalid
         */
        this.addEntryForm.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(data => {
            this.disableSaveButton =
                !this.addEntryForm.get("foodName").valid || !this.addEntryForm.get("calories").valid ||
                !this.addEntryForm.get("eatingTime").valid || !this.addEntryForm.get("eatingDate").valid ||
                !this.addEntryForm.controls['eatingDate'].value.day || 
                (!!this.isComingFromAdmin && !this.addEntryForm.get("username").valid);
        });
    }

    private initializeForm(): void {
        /**
         * Function to initialize ann entry reactive form
         */

        const currentDate = new Date();
        const today = `${currentDate.getDate()}-${(currentDate.getMonth() + 1)}-${currentDate.getFullYear()}`

        this.addEntryForm = this.formBuilder.group({
            entryDate: [{ value: today, disabled: true }, []],
            eatingDate: ["", [Validators.required]],
            eatingTime: ["", [Validators.required]],
            userId: ["", []],
            username: ["", [Validators.required]],
            foodName: ["", [Validators.required]],
            calories: ["", [Validators.required, Validators.min(0)]]
        });
    }

    ngOnInit(): void {
        this.currentUser = JSON.parse(localStorage.getItem("currentUser"));
        this.initializeForm();
        this.subscribeToFormValueChanges();
        this.getUsersList();
    }

    // Public functions called from UI starts ------------------------------------------------------

    public onCancelBtnClick(): void {
        /**
         * FUnction that is called from Ui when cancel button is clicke don the modal.
         * Closes all the modals.
         */
        this.modalService.closeAll();
    }

    public setCaloriesFromTypeahead(data: any): void {
        /**
         * Function to set the calories from the select typeahead value
         */
        if (!!data.item.calories)
            this.addEntryForm.controls['calories'].setValue(data.item.calories);
        else {
            this.loading = true
            this.autocompleteService.getCalories(data.item.foodName).pipe(takeUntil(this.destroy$))
                .subscribe(res => {
                    if (!!res.success && res.calories)
                        this.addEntryForm.controls['calories'].setValue(res.calories);
                    this.loading = false;
                })
        }
    }

    public onSaveButtonClick(): void {
        /**
         * Function to emit maon button click
         */

        const eatingTime = `${this.addEntryForm.controls['eatingDate'].value.year}-` +
            `${this.addEntryForm.controls['eatingDate'].value.month}-` +
            `${this.addEntryForm.controls['eatingDate'].value.day} ` +
            `${this.addEntryForm.controls['eatingTime'].value.hour}:` +
            `${this.addEntryForm.controls['eatingTime'].value.minute}:` +
            `${this.addEntryForm.controls['eatingTime'].value.second}`;

        const foodName = this.addEntryForm.controls["foodName"].value.foodName ||
            this.addEntryForm.controls["foodName"].value;

        const request: FoodEntryRequest = {
            userId: !!this.isComingFromAdmin ? this.addEntryForm.controls["username"].value : this.currentUser.id,
            foodName: foodName,
            eatingTime: eatingTime,
            calories: this.addEntryForm.controls["calories"].value,
        }
        this.modalService.closeAll();
        this.onMainButtonClick.emit(request);
    }
}