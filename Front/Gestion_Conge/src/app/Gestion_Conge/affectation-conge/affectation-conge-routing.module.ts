import { NgModule } from '@angular/core';

import { AffectationCongeComponent } from './affectation-conge.component';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: AffectationCongeComponent }
    ])],
    exports: [RouterModule]
})
export class AffectationCongeRoutingModule { }
