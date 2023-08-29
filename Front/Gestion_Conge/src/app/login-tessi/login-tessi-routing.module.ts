import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginTessiComponent } from './login-tessi.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '/login', component: LoginTessiComponent }
    ])],
    exports: [RouterModule]
})
export class LoginTessiRoutingModule { }