import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ExportExcelComponent } from './export-excel.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: ExportExcelComponent }
    ])],
    exports: [RouterModule]
})
export class ExportExcelRoutingModule { }
