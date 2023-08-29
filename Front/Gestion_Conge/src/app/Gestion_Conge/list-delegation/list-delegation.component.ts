import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { th } from 'date-fns/locale';
import { MenuItem, MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { forkJoin } from 'rxjs';
import { Conge } from 'src/app/Model/Conge';
import { Employee } from 'src/app/Model/Employee';
import { MotifRefus } from 'src/app/Model/MotifRefus';
import { Politique } from 'src/app/Model/Politique';
import { TypeConge } from 'src/app/Model/TypeConge';
import { CongeServicesService } from 'src/app/Services/Conge/conge-services.service';
import { PolitiqueService } from 'src/app/Services/Politique/politique.service';
import { Product } from 'src/app/demo/api/product';


@Component({
  providers: [MessageService],
  templateUrl: './list-delegation.component.html',

})
export class ListDelegationComponent implements OnInit {

  delegationDialog: boolean = false;

  conges: Conge[] = [];

  conge: Conge = {
    idConge: 0,
    dateDemande: '',
    dateDebut: '',
    dateFin: '',
    duree: 0,
    etat: '',
    description: '',
    pieceJointe: '',
    idDelegue: 0,
    dateValidation: '',
    typeConge: new TypeConge,
    demandeur: new Employee,
    validateur: new Employee,
    motifRefus: new MotifRefus
  };


  selectedPolitiques: Product[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];

  employes: Employee[] = [];

  startDate: Date;
  endDate: Date;

  selectedEmployeeId: number;

  panelMenuItems: MenuItem[] = [];

  items: MenuItem[] = [];

  rowsPerPageOptions = [5, 10, 20];

  constructor(private messageService: MessageService, private congeserv: CongeServicesService, private datePipe: DatePipe) { }


  employe: Employee

  idConge: number;

  listeConges: any[] = [];


  ngOnInit() {
    this.congeserv.getCongesSuperviseurs().subscribe({
      next: (data) => {
        this.conges = data;
        this.loadDelegueEmployeeNames();
      },
      error: (error) => {
        console.error('Une erreur est survenue : ', error);
      }
    });
    this.loadMenuItems();


  }
  delegueEmployeeNames: { [idConge: number]: string } = {};


  loadDelegueEmployeeNames(): void {
    this.conges.forEach((conge) => {
      this.congeserv.getDelegueEmployeeName(conge.idConge).subscribe(
        (response) => {
          this.delegueEmployeeNames[conge.idConge] = response;
        },
        (error) => {
          console.error('Une erreur est survenue : ', error);
        }
      );
    });
  }


  congeDelegueStatus(conge: Conge): string {
    return conge.idDelegue ? 'Délégué' : 'Non délégué';
  }


  hideDialog() {
    this.delegationDialog = false;
    this.submitted = false;
  }

  openNew(conge: Conge) {
    this.conge = conge
    this.submitted = false;
    this.delegationDialog = true;
    const dateObject = new Date(conge.dateDebut);
    this.startDate = dateObject;
    const dateObject2 = new Date(conge.dateFin);
    this.endDate = dateObject2;
  }

  loadMenuItems(): void {
    this.congeserv.getEmployeesDelegDev().subscribe(delegEmployees => {
      this.panelMenuItems.push({
        label: 'Développeurs',
        items: this.convertToMenuItems(delegEmployees),

      });
    });

    this.congeserv.getEmployeesDelegContr().subscribe(controleEmployees => {
      this.panelMenuItems.push({
        label: 'Contrôleurs',
        items: this.convertToMenuItems(controleEmployees)
      });
    });
  }

  convertToMenuItems(employees: Employee[]): MenuItem[] {
    console.log('Converting employees:', employees);

    const menuItems = employees.map(employee => ({
      label: employee.nom,
      command: () => this.onEmployeeClick(employee.idEmp)

    }));

    console.log('Converted menu items:', menuItems);

    return menuItems;

  }

  onEmployeeClick(employeeId: number) {
    this.selectedEmployeeId = employeeId;
    console.log(this.selectedEmployeeId)
  }

  saveDelegation() {
    if (this.selectedEmployeeId) {

      const formattedDate1 = this.datePipe.transform(this.startDate, "yyyy-MM-dd'T'HH:mm:ss");
      const formattedDate2 = this.datePipe.transform(this.endDate, "yyyy-MM-dd'T'HH:mm:ss");
      console.log(formattedDate1)
      console.log(formattedDate2)
      this.congeserv.delegationRole(this.selectedEmployeeId, formattedDate1, formattedDate2).subscribe(response => {
        if (response.message === "OK") {
          console.log(response.message);
          this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Délegation effectué avec succés ', life: 5000 });
          this.delegationDialog = false;
        }
        else (response.message != "OK")
        {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: response.message, life: 3000 });
        }
      }
      );
    }
  }



  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }




}
