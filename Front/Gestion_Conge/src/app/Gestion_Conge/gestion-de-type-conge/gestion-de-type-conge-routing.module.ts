import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { GestionDeTypeCongeComponent } from './gestion-de-type-conge.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: GestionDeTypeCongeComponent }
    ])],
    exports: [RouterModule]
})
export class GestionDeTypeCongeRoutingModule { }
