import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ViewMyCongeComponent } from './view-my-conge.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: ViewMyCongeComponent }
    ])],
    exports: [RouterModule]
})
export class ViewMyCongeRoutingModule { }
