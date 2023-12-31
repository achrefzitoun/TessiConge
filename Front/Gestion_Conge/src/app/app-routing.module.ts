import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { NotfoundComponent } from './demo/components/notfound/notfound.component';
import { AppLayoutComponent } from "./layout/app.layout.component";
import { LoginTessiComponent } from './login-tessi/login-tessi.component';
import { CrudEmployeeComponent } from './crud-employee/crud-employee.component';

@NgModule({
    imports: [
        RouterModule.forRoot([

            {
                path: '', component: AppLayoutComponent,
                children: [
                    { path: '', loadChildren: () => import('./demo/components/dashboard/dashboard.module').then(m => m.DashboardModule) },
                    { path: 'uikit', loadChildren: () => import('./demo/components/uikit/uikit.module').then(m => m.UIkitModule) },
                    { path: 'utilities', loadChildren: () => import('./demo/components/utilities/utilities.module').then(m => m.UtilitiesModule) },
                    { path: 'documentation', loadChildren: () => import('./demo/components/documentation/documentation.module').then(m => m.DocumentationModule) },
                    { path: 'blocks', loadChildren: () => import('./demo/components/primeblocks/primeblocks.module').then(m => m.PrimeBlocksModule) },
                    { path: 'pages', loadChildren: () => import('./demo/components/pages/pages.module').then(m => m.PagesModule) },
                    { path: 'gestionconge', loadChildren: () => import('./Gestion_Conge/gestion-conge.module').then(m => m.GestionConge) },
                    { path: 'gestionroles', loadChildren: () => import('./Gestion_Roles/gestion-role.module').then(m => m.GestionRole) },
                ]
            },
            { path: 'gestionconge', loadChildren: () => import('./Gestion_Conge/gestion-conge.module').then(m => m.GestionConge) },
            { path: 'gestionroles', loadChildren: () => import('./Gestion_Roles/gestion-role.module').then(m => m.GestionRole) },
            { path: 'auth', loadChildren: () => import('./demo/components/auth/auth.module').then(m => m.AuthModule) },
            { path: 'landing', loadChildren: () => import('./demo/components/landing/landing.module').then(m => m.LandingModule) },
            { path: 'notfound', component: NotfoundComponent },
            { path: 'login', component: LoginTessiComponent},
            { path: 'crudEmployee', component: CrudEmployeeComponent},
            { path: '**', redirectTo: '/notfound' },
        ], { scrollPositionRestoration: 'enabled', anchorScrolling: 'enabled', onSameUrlNavigation: 'reload' })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
