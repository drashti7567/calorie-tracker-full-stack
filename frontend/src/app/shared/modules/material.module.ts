import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { MatSelectModule } from "@angular/material/select";
import {
    NgbAccordionModule, NgbDatepickerModule, NgbDropdownModule,
    NgbNavModule, NgbTimepickerModule, NgbTypeaheadModule
} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        CommonModule,
        NgbTypeaheadModule,
        NgbNavModule,
        NgbAccordionModule,
        NgbDropdownModule,
        NgbDatepickerModule,
        NgbTimepickerModule,
        MatSelectModule,
        // MatTabsModule,
        // MatInputModule,
        MatIconModule,
        // MatExpansionModule,
        MatButtonModule,
        // MatDatepickerModule,
        MatSlideToggleModule,
        // MatInputModule,
        // MatRippleModule,
        // MatToolbarModule,
        // MatFormFieldModule,
        // MatTooltipModule,
        // MatSelectModule,
        // MatCardModule,
        // MatAutocompleteModule,
        // MatNativeDateModule,
        // MatCheckboxModule,
        // MatMenuModule,
        // MatProgressSpinnerModule
    ],
    declarations: [
    ],
    providers: [
    ],
    exports: [
        FormsModule,
        RouterModule,
        CommonModule,
        ReactiveFormsModule,
        NgbTypeaheadModule,
        NgbNavModule,
        NgbAccordionModule,
        NgbDropdownModule,
        NgbDatepickerModule,
        NgbTimepickerModule,
        MatSelectModule,
        // MatTabsModule,
        // MatInputModule,
        MatIconModule,
        // MatExpansionModule,
        MatButtonModule,
        // MatDatepickerModule,
        MatSlideToggleModule,
        // MatInputModule,
        // MatRippleModule,
        // MatToolbarModule,
        // MatFormFieldModule,
        // MatTooltipModule,
        // MatSelectModule,
        // MatCardModule,
        // MatMenuModule,
        // MatAutocompleteModule,
        // MatNativeDateModule,
        // MatCheckboxModule,
        // MatProgressSpinnerModule,
    ]
})
export class MaterialModule { }
