import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { GestionJourFerieComponent } from './gestion-jour-ferie.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: GestionJourFerieComponent }
    ])],
    exports: [RouterModule]
})
export class GestionJourFerieRoutingModule { }
