import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PolitiqueComponent } from './politique.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: PolitiqueComponent }
    ])],
    exports: [RouterModule]
})
export class PolitiqueRoutingModule { }
