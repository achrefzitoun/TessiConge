import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CrudRolesComponent } from './crud-roles.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: CrudRolesComponent }
    ])],
    exports: [RouterModule]
})
export class CrudRolesRoutingModule { }
