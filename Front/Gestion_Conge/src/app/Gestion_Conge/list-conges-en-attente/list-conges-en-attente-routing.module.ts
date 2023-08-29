import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ListCongesEnAttenteComponent } from './list-conges-en-attente.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: ListCongesEnAttenteComponent }
    ])],
    exports: [RouterModule]
})
export class ListCongesEnAttenteRoutingModule { }
