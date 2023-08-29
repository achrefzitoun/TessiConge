import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { EmployeeService } from 'src/app/Services/Employee/employee.service';
import { Employee } from '../Model/Employee';
@Component({
  selector: 'app-crud-employee',
  templateUrl: './crud-employee.component.html',
  providers: [MessageService]
})
export class CrudEmployeeComponent implements OnInit {

    EmployeetDialog: boolean = false;

    deleteEmployeeDialog: boolean = false;

    deleteEmployeesDialog: boolean = false;

    employees: Employee[] = [];

    employee: Employee;

    selectedEmployees: Employee[] = [];

    submitted: boolean = false;

    cols: any[] = [];

    statuses: any[] = [];

    rowsPerPageOptions = [5, 10, 20];

    constructor(private employeeService: EmployeeService, private messageService: MessageService) { }

    ngOnInit() {
        this.employeeService.getEmployees().then(data => this.employees = data);

        this.cols = [
            { field: 'Username', header: 'Username' },
            { field: 'Email', header: 'Email' },
            { field: 'Entité', header: 'Entité' },
            { field: 'Solde Congé', header: 'Solde Congé' },
            { field: 'Status', header: 'Status' }
        ];

        this.statuses = [
            { label: 'Developpement', value: 'Developpement' },
            { label: 'Qualite', value: 'Qualite' },
            { label: 'RH', value: 'RH' },
            { label: 'Gestion&Assurance', value: 'Gestion&Assurance' },
            { label: 'Service Client', value: 'Service Client' }
        ];
    }

    openNew() {
        this.employee = new Employee();
        this.submitted = false;
        this.EmployeetDialog = true;
    }

    deleteSelectedEmployees() {
        this.deleteEmployeesDialog = true;
    }

    editEmployee(employee: Employee) {
        this.employee = { ...employee };
        this.EmployeetDialog = true;
    }

    deleteEmployee(employee: Employee) {
        this.deleteEmployeeDialog = true;
        this.employee = { ...employee };
    }

    confirmDeleteSelected() {
        this.deleteEmployeesDialog = false;
        this.employees = this.employees.filter(val => !this.selectedEmployees.includes(val));
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Employees Deleted', life: 3000 });
        this.selectedEmployees = [];
    }

    confirmDelete() {
        this.deleteEmployeeDialog = false;
        this.employees = this.employees.filter(val => val.idEmp !== this.employee.idEmp);
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Employee Deleted', life: 3000 });
        this.employee = new Employee();
    }

    hideDialog() {
        this.EmployeetDialog = false;
        this.submitted = false;
    }

    saveEmployee() {
        this.submitted = true;

        if (this.employee.nom?.trim()) {
            if (this.employee.idEmp) {
                // @ts-ignore
                this.employee.statut = this.employee.statut.value ? this.employee.statut.value : this.employee.statut;
             //   this.employees[this.findIndexById(this.employee.idEmp)] = this.employee;
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Employee Updated', life: 3000 });
            } else {
                /*this.employee.idEmp = this.createId();
                const generatedUsername = this.generateUsername(this.employee.nom, this.employee.idEmp);
                this.employee.nom = generatedUsername; // Ajoutez le nom d'utilisateur au nouvel employé
                // @ts-ignore
                this.employee.statut = 'Actif'
                this.employees.push(this.employee);
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Employee Created', life: 3000 });*/
            }

            this.employees = [...this.employees];
            this.EmployeetDialog = false;
           // this.employee = {};
        }
    }

    generateUsername(employeeName: string, employeeId: string): string {
        const formattedName = employeeName.replace(/\s+/g, ''); // Remove spaces from employee name
        const username = `${formattedName}${employeeId}`;
        return username;
      }

    findIndexById(id: string): number {
        let index = -1;
        for (let i = 0; i < this.employees.length; i++) {
       /*     if (this.employees[i].idEmp === id) {
                index = i;
                break;
            }*/
        }

        return index;
    }

    createId(): string {
        let id = '';
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        for (let i = 0; i < 5; i++) {
            id += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return id;
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

}
