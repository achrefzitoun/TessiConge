import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AutorisationComponent } from './autorisation.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: AutorisationComponent }
    ])],
    exports: [RouterModule]
})
export class AutorisationRoutingModule { }
