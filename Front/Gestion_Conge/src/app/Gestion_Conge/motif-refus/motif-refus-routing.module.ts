import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MotifRefusComponent } from './motif-refus.component';


@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: MotifRefusComponent }
    ])],
    exports: [RouterModule]
})
export class MotifRefusRoutingModule { }
