import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AffectationCongeComponent } from './affectation-conge.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: AffectationCongeComponent }
    ])],
    exports: [RouterModule]
})
export class AffectationCongeRoutingModule { }
