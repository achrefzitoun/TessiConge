import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [RouterModule.forChild([        
        { path: 'ListDemande', loadChildren: () => import('./list-demande/list-demande.module').then(m => m.ListDemandeModule) },
        { path: 'MotifRefus', loadChildren: () => import('./motif-refus/motif-refus.module').then(m => m.MotifRefusModule) },
        { path: 'Delegation', loadChildren: () => import('./delegation/delegation.module').then(m => m.DelegationModule) },
        { path: 'Politique', loadChildren: () => import('./politique/politique.module').then(m => m.PolitiqueModule) },
        { path: 'EnAttente', loadChildren: () => import('./list-conges-en-attente/list-conges-en-attente.module').then(m => m.ListCongesEnAttenteModule) },
        { path: 'AllConge', loadChildren: () => import('./view-all-conge/view-all-conge.module').then(m => m.ViewAllCongeModule) },
        { path: 'MyConge', loadChildren: () => import('./view-my-conge/view-my-conge.module').then(m => m.ViewMyCongeModule) },
        { path: 'ListDelegation', loadChildren: () => import('./list-delegation/list-delegation.module').then(m => m.ListDelegationModule) },
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
