import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GestionJourFerieRoutingModule } from './gestion-jour-ferie-routing.module';
import { FormsModule } from '@angular/forms';
import { GestionJourFerieComponent } from './gestion-jour-ferie.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule } from 'primeng/calendar';
import { BrowserModule } from '@angular/platform-browser';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { TableModule } from 'primeng/table';
import { MultiSelectModule } from 'primeng/multiselect';

@NgModule({
    imports: [
        CommonModule,
        GestionJourFerieRoutingModule,
        CalendarModule,
        FormsModule,
        DialogModule,
        ConfirmDialogModule,
        ToastModule,
        ToolbarModule,
        InputTextModule,
        InputTextareaModule,
        TableModule,
        MultiSelectModule
       
    ],
    declarations: [GestionJourFerieComponent]
})
export class GestionJourFerieModule { }
