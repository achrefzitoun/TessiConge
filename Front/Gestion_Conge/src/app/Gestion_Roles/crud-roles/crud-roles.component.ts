import { Component } from '@angular/core';
import { flexibleCompare } from '@fullcalendar/core/internal';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { Employee } from 'src/app/Model/Employee';
import { Role } from 'src/app/Model/Role';
import { RoleServicesService } from 'src/app/Services/Role/role-services.service';

@Component({
    templateUrl: './crud-roles.component.html',
    providers: [MessageService]
})
export class CrudRolesComponent {

    roleDialog: boolean = false;

    deleteRoleDialog: boolean = false;

    deleteRolesDialog: boolean = false;

    employeeDiaglog: boolean = false;

    employees: Employee[] = [];

    roles: Role[];

    role: Role = {
        idRole: 0,
        nomRole: '',
        description: '',
        employees: []
    };

    selectedRoles: Role[];

    submitted: boolean = false;

    cols: any[] = [];

    statuses: any[] = [];

    rowsPerPageOptions = [5, 10, 20];

    constructor(private roleServices: RoleServicesService, private messageService: MessageService) { }

    ngOnInit() {
        this.roleServices.retrieveAllRoles().subscribe(data => this.roles = data);
    }

    openNew() {
        this.role = new Role;
        this.submitted = false;
        this.roleDialog = true;
    }

    deleteSelectedRoles() {
        this.deleteRolesDialog = true;
    }

    editRole(role: Role) {
        this.role = { ...role };
        this.roleDialog = true;
    }

    deleteRole(role: Role) {
        this.deleteRoleDialog = true;
        this.role = { ...role };
    }

    confirmDeleteSelected() {
        this.deleteRolesDialog = false;
        for (let i = 0; i < this.selectedRoles.length; i++) {
            this.roleServices.deleteRole(this.selectedRoles[i].idRole).subscribe();
            this.roles = this.roles.filter(val => val.idRole !== this.selectedRoles[i].idRole);
        }
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Roles Deleted', life: 3000 });
        this.selectedRoles = [];
    }

    confirmDelete() {
        this.deleteRoleDialog = false;
        this.roleServices.deleteRole(this.role.idRole).subscribe();
        this.roles = this.roles.filter(val => val.idRole !== this.role.idRole);
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Role Deleted', life: 3000 });
        this.role = null;
    }

    hideDialog() {
        this.roleDialog = false;
        this.submitted = false;
    }

    saveRole() {
        this.submitted = true;
        if (this.role.idRole) {
            this.roleServices.updateRole(this.role).subscribe(
                updatedRole => {
                    const index = this.roles.findIndex(role => role.idRole === updatedRole.idRole);
                    if (index !== -1) {
                        this.roles[index] = updatedRole;
                    }

                    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
                }
            );
        }
        else {
            this.roleServices.addRole(this.role).subscribe((addedRole) => {
                this.roles.push(addedRole);
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Role Created', life: 3000 });
            });
        }

        this.roles = [...this.roles];
        this.roleDialog = false;
        this.role = null;
    }

    viewEmployees(role: Role) {
        this.roleServices.getEmployeesByRole(role.idRole).subscribe(
            (employees) => {
                this.employees = employees;
            }
        );
        this.employeeDiaglog = true;
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }
}
