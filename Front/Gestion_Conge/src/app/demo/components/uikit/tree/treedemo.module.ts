import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TreeDemoComponent } from './treedemo.component';
import { TreeDemoRoutingModule } from './treedemo-routing.module';
import { TreeModule } from 'primeng/tree';
import { TreeTableModule } from 'primeng/treetable';
import { FormsModule } from '@angular/forms';

@NgModule({
	imports: [
		CommonModule,
		TreeDemoRoutingModule,
		FormsModule,
		TreeModule,
		TreeTableModule
	],
	declarations: [TreeDemoComponent],
})
export class TreeDemoModule { }
