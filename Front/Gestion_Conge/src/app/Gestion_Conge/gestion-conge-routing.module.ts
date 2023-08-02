import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [RouterModule.forChild([
        { path: 'AffectationConge', loadChildren: () => import('./affectation-conge/affectation-conge.module').then(m => m.AffectationCongeModule) },
        { path: 'ReponseConge', loadChildren: () => import('./reponse-conge/reponse-conge.module').then(m => m.ResponseCongeModule) },
        { path: '**', redirectTo: '/notfound' }
    ])],
    exports: [RouterModule]
})

export class GestionCongeRoutingModule { }
