
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExportExcelComponent } from './export-excel.component';
import { ExportExcelRoutingModule } from './export-excel-routing.module';
import { FormsModule } from '@angular/forms';
import { ToolbarModule } from 'primeng/toolbar';
import { CalendarModule } from 'primeng/calendar';


@NgModule({
    imports: [
        CommonModule,
        ExportExcelRoutingModule,
        FormsModule,
        ToolbarModule,
        CalendarModule
            
    ],
    declarations: [ExportExcelComponent]
})
export class ExportExcelModule { }