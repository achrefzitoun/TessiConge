import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DelegationComponent } from './delegation.component';


@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: DelegationComponent }
    ])],
    exports: [RouterModule]
})
export class DelegationRoutingModule { }
