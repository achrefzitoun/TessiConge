import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ListDelegationComponent } from './list-delegation.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: ListDelegationComponent }
    ])],
    exports: [RouterModule]
})
export class ListDelegationRoutingModule { }
