import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReponseCongeRoutingModule } from './response-conge-routing.module';
import { ReponseCongeComponent } from './reponse-conge.component';

@NgModule({
    imports: [
        CommonModule,
        ReponseCongeRoutingModule
    ],
    declarations: [ReponseCongeComponent]
})
export class ResponseCongeModule { }
