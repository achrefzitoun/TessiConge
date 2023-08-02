import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
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

  roles: Role[] = [];

  role: Role;

  selectedRoles: Role[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];

  rowsPerPageOptions = [5, 10, 20];

  constructor(private roleService: RoleServicesService, private messageService: MessageService) { }

  ngOnInit() {
      this.roleService.retrieveAllRoles().subscribe(data => this.roles = data);

      this.cols = [
          { field: 'product', header: 'Product' },
          { field: 'price', header: 'Price' },
          { field: 'category', header: 'Category' },
          { field: 'rating', header: 'Reviews' },
          { field: 'inventoryStatus', header: 'Status' }
      ];

      this.statuses = [
          { label: 'INSTOCK', value: 'instock' },
          { label: 'LOWSTOCK', value: 'lowstock' },
          { label: 'OUTOFSTOCK', value: 'outofstock' }
      ];
  }

  openNew() {
      this.role = new Role();
      this.submitted = false;
      this.roleDialog = true;
  }

  deleteSelectedRoles() {
      this.deleteRolesDialog = true;
  }

  editProduct(role: Role) {
      this.role = { ...role };
      this.roleDialog = true;
  }

  deleteProduct(role: Role) {
      this.deleteRoleDialog = true;
      this.role = { ...role };
  }

  confirmDeleteSelected() {
      this.deleteRolesDialog = false;
      this.roles = this.roles.filter(val => !this.selectedRoles.includes(val));
      this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
      this.selectedRoles = [];
  }

  confirmDelete() {
      this.deleteRoleDialog = false;
      this.roles = this.roles.filter(val => val.idRole !== this.role.idRole);
      this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000 });
      //this.role = {};
  }

  hideDialog() {
      this.roleDialog = false;
      this.submitted = false;
  }

  saveRole() {
      this.submitted = true;
/*
      if (this.product.name?.trim()) {
          if (this.product.id) {
              // @ts-ignore
              this.product.inventoryStatus = this.product.inventoryStatus.value ? this.product.inventoryStatus.value : this.product.inventoryStatus;
              this.products[this.findIndexById(this.product.id)] = this.product;
              this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
          } else {
              this.product.id = this.createId();
              this.product.code = this.createId();
              this.product.image = 'product-placeholder.svg';
              // @ts-ignore
              this.product.inventoryStatus = this.product.inventoryStatus ? this.product.inventoryStatus.value : 'INSTOCK';
              this.products.push(this.product);
              this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000 });
          }

          this.products = [...this.products];
          this.productDialog = false;
          this.product = {};
      }*/
  }

  findIndexById(id: string) {
   /*   let index = -1;
      for (let i = 0; i < this.products.length; i++) {
          if (this.products[i].id === id) {
              index = i;
              break;
          }
      }

      return index;*/
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
