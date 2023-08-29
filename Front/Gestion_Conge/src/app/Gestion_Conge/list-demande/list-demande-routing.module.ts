import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ListDemandeComponent } from './list-demande.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: ListDemandeComponent }
    ])],
    exports: [RouterModule]
})
export class ListDemandeRoutingModule { }
