import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [RouterModule.forChild([
        { path: 'view', loadChildren: () => import('./crud-roles/crud-roles.module').then(m => m.CrudRolesModule) },
        { path: '**', redirectTo: '/notfound' }
    ])],
    exports: [RouterModule]
})

export class GestionRoleRoutingModule {}
