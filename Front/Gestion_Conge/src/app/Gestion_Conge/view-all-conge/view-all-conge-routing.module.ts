import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ViewAllCongeComponent } from './view-all-conge.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: ViewAllCongeComponent }
    ])],
    exports: [RouterModule]
})
export class ViewAllCongeRoutingModule { }
