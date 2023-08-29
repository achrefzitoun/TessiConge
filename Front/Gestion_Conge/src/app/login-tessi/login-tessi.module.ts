import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginTessiRoutingModule } from './login-tessi-routing.module';

import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { FormsModule } from '@angular/forms';
import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { AppLayoutModule } from '../layout/app.layout.module';
import { RippleModule } from 'primeng/ripple';

@NgModule({
    imports: [
        CommonModule,
        LoginTessiRoutingModule,
        ButtonModule,
        CheckboxModule,
        InputTextModule,
        FormsModule,
        PasswordModule,
        AppLayoutModule,
        
        RippleModule,
        
    ],
    declarations: []
})
export class LoginTessiModule { }
