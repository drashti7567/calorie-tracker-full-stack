import { Component, OnInit, Input, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { FoodEntryRequest } from 'app/shared/models/request/foodEntry/food-entry-request.model';
import { FoodEntryFields } from 'app/shared/models/response/foodEntry/get-food-entries-response.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { ModalService } from '../../components/modal/modal.service';

@Component({
    selector: 'cc-add-entry-modal',
    templateUrl: './add-entry-modal.component.html',
    styleUrls: ['./add-entry-modal.component.scss']
})
export class AddEntryModalComponent implements OnInit {
    
    @Output("onMainButtonClick") onMainButtonClick = new EventEmitter();

    constructor(private modalService: ModalService, private formBuilder: FormBuilder) {}

    private currentUser;
    public addEntryForm;
    private destroy$: Subject<boolean> = new Subject<boolean>();

    public disableSaveButton = true;

    private subscribeToFormValueChanges(): void {
        /**
         * FUnction that disbaled the save button whenever there is a change in inputs 
         * and inputs are invalid
         */
        this.addEntryForm.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(data => {
            this.disableSaveButton =
                !this.addEntryForm.get("foodName").valid || !this.addEntryForm.get("calories").valid ||
                !this.addEntryForm.get("eatingTime").valid;
        });
    }

    private initializeForm(): void {
        /**
         * Function to initialize ann entry reactive form
         */

         const currentDate = new Date();
         const today = `${currentDate.getDate()}-${(currentDate.getMonth() + 1)}-${currentDate.getFullYear()}`

        this.addEntryForm = this.formBuilder.group({
            entryDate: [{value: today, disabled: true}, []],
            eatingTime: ["", [Validators.required]],
            foodName: ["", [Validators.required]],
            calories: ["", [Validators.required, Validators.min(0)]]
        });
    }

    ngOnInit(): void {
        this.currentUser = JSON.parse(localStorage.getItem("currentUser"));
        this.initializeForm();
        this.subscribeToFormValueChanges();
    }

    // Public functions called from UI starts ------------------------------------------------------

    public onCancelBtnClick(): void {
        /**
         * FUnction that is called from Ui when cancel button is clicke don the modal.
         * Closes all the modals.
         */
        this.modalService.closeAll();
    }

    public onSaveButtonClick(): void {
        /**
         * Function to emit maon button click
         */
        const request: FoodEntryRequest = {
            userId: this.currentUser.id,
            foodName: this.addEntryForm.controls["foodName"].value,
            eatingTime: this.addEntryForm.controls["eatingTime"].value,
            calories: this.addEntryForm.controls["calories"].value,
        }
        this.modalService.closeAll();
        this.onMainButtonClick.emit(request);
    }
}