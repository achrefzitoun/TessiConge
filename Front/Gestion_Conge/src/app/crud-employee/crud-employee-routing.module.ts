import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CrudEmployeeComponent } from './crud-employee.component';
@NgModule({
	imports: [RouterModule.forChild([
		{ path: '', component: CrudEmployeeComponent }
	])],
	exports: [RouterModule]
})
export class CrudEmployeeRoutingModule { }