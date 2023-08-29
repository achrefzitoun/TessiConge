import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {

    model: any[] = [];
    queryParams: any;
    constructor(public layoutService: LayoutService) { }

    ngOnInit() {

        this.model = [
            {
                label: 'Home',
                items: [
                    { label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['/'] }
                ]
            },
            {
                label: 'Admin',
                items: [
                    { label: 'List des demandes', icon: 'pi pi-fw pi-calendar', routerLink: ['/gestionconge/EnAttente'] },
                    { label: 'Affecter un congé', icon: 'pi pi-fw pi-reply', routerLink: ['/gestionconge/AffectationConge'] },
                    { label: 'Gestion des roles', icon: 'pi pi-fw pi-briefcase', routerLink: ['/gestionroles/view'] },
                    {
                        label: 'Gestion des jours fériés', icon: 'pi pi-fw pi-bookmark',
                        items: [
                            { label: 'Calendrier tunisienne', icon: 'pi pi-fw pi-calendar', routerLink: ['/gestionconge/JourFerieTn'], queryParams: { id: 1 } },
                            { label: 'Calendrier française', icon: 'pi pi-fw pi-calendar', routerLink: ['/gestionconge/JourFerieFr'], queryParams: { id: 2 } },
                        ]
                    },
                    { label: 'Gestion des types de congés', icon: 'pi pi-fw pi-book', routerLink: ['/gestionconge/TypeConge'] },
                    { label: 'Gestion des politiques', icon: 'pi pi-fw pi-book', routerLink: ['/gestionconge/Politique'] },
                    { label: 'Gestion des motifs de refus', icon: 'pi pi-fw pi-book', routerLink: ['/gestionconge/MotifRefus'] },
                    { label: 'Gestion des délegations', icon: 'pi pi-fw pi-book', routerLink: ['/gestionconge/ListDelegation'] },
                    { label: 'ExportConge', icon: 'pi pi-fw pi-book', routerLink: ['/gestionconge/Export'] },

                ]
            },
            {
                label: 'Gestion de congé',
                items: [
                    { label: 'Congés', icon: 'pi pi-fw pi-calendar', routerLink: ['/gestionconge/ListDemande'] },
                    { label: 'Autorisation', icon: 'pi pi-fw pi-calendar', routerLink: ['/gestionconge/Autorisation'] },
                    { label: 'List des congés', icon: 'pi pi-fw pi-calendar', routerLink: ['/gestionconge/AllConge'] },
                ]
            },
        ];
    }
}
