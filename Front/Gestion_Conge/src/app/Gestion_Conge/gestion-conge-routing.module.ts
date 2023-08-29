import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [RouterModule.forChild([
        { path: 'AffectationConge', loadChildren: () => import('./affectation-conge/affectation-conge.module').then(m => m.AffectationCongeModule) },
        { path: 'ReponseConge', loadChildren: () => import('./reponse-conge/reponse-conge.module').then(m => m.ResponseCongeModule) },
      //  { path: 'JourFerie', loadChildren: () => import('./gestion-jour-ferie/gestion-jour-ferie.module').then(m => m.GestionJourFerieModule) }, 
        { path: 'JourFerieTn', loadChildren: () => import('./gestion-jour-ferie/gestion-jour-ferie.module').then(m => m.GestionJourFerieModule) },  
        { path: 'JourFerieFr', loadChildren: () => import('./gestion-jour-ferie/gestion-jour-ferie.module').then(m => m.GestionJourFerieModule) },  
        { path: 'Autorisation', loadChildren: () => import('./autorisation/autorisation.module').then(m => m.AutorisationModule) },  
        { path: 'TypeConge', loadChildren: () => import('./gestion-de-type-conge/gestion-de-type-conge.module').then(m => m.GestionDeTypeCongeModule) },
        { path: '**', redirectTo: '/notfound' }
    ])],
    exports: [RouterModule]
})

export class GestionCongeRoutingModule { }
