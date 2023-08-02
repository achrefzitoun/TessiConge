import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReponseCongeComponent } from './reponse-conge.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: ReponseCongeComponent }
    ])],
    exports: [RouterModule]
})
export class ReponseCongeRoutingModule { }
