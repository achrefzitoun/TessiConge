import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { MultiSelectModule } from 'primeng/multiselect';
import { GestionDeTypeCongeRoutingModule } from './gestion-de-type-conge-routing.module';
import { GestionDeTypeCongeComponent } from './gestion-de-type-conge.component';
import { RatingModule } from 'primeng/rating';
import { ButtonModule } from 'primeng/button';
import { SliderModule } from 'primeng/slider';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { RippleModule } from 'primeng/ripple';
import { DropdownModule } from 'primeng/dropdown';
import { ProgressBarModule } from 'primeng/progressbar';
import { ToolbarModule } from 'primeng/toolbar';
import { DialogModule } from 'primeng/dialog';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectButtonModule } from 'primeng/selectbutton';
import { CascadeSelectModule } from 'primeng/cascadeselect';

@NgModule({
    imports: [
        CommonModule,
        GestionDeTypeCongeRoutingModule,
        FormsModule,
		TableModule,
		RatingModule,
		ButtonModule,
		SliderModule,
		InputTextModule,
		ToggleButtonModule,
		RippleModule,
		MultiSelectModule,
		DropdownModule,
		ProgressBarModule,
		ToastModule,
        ToolbarModule,
        DialogModule,
        InputTextareaModule,
        InputNumberModule,
        SelectButtonModule,
        CascadeSelectModule
    ],
    declarations: [GestionDeTypeCongeComponent]
})
export class GestionDeTypeCongeModule { }