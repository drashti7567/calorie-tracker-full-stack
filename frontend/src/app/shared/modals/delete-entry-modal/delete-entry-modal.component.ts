import { Component, OnInit, Input, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbCalendar, NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { FoodEntryRequest } from 'app/shared/models/request/foodEntry/food-entry-request.model';
import { FoodEntryFields } from 'app/shared/models/response/foodEntry/get-food-entries-response.model';
import { ProductWithCalorieResponse } from 'app/shared/models/response/searchFood/product-with-calorie-response.model';
import { AutoCompleteService } from 'app/shared/services/autocomplete.service';
import { map } from 'jquery';
import { Observable, of, Subject } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, switchMap, takeUntil, tap } from 'rxjs/operators';

import { ModalService } from '../../components/modal/modal.service';

@Component({
    selector: 'cc-delete-entry-modal',
    templateUrl: './delete-entry-modal.component.html',
    styleUrls: ['./delete-entry-modal.component.scss']
})
export class DeleteEntryModalComponent implements OnInit, OnChanges {

    @Input("entry") entry: FoodEntryFields;

    @Output("onMainButtonClick") onMainButtonClick = new EventEmitter();

    constructor(private modalService: ModalService) { }

    public addEntryForm;
    public entryId;

    ngOnInit(){}

    ngOnChanges(): void {
        this.entryId = this.entry && this.entry.entryId ? this.entry.entryId : ""; 
    }

    // Public functions called from UI starts ------------------------------------------------------

    public onCancelBtnClick(): void {
        /**
         * FUnction that is called from Ui when cancel button is clicke don the modal.
         * Closes all the modals.
         */
        this.modalService.closeAll();
    }

    public onDeleteButtonClick(): void {
        /**
         * Function to emit maon button click
         */

        this.modalService.closeAll();
        this.onMainButtonClick.emit(this.entryId);
    }
}