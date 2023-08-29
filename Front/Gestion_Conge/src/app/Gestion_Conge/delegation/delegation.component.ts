import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { Employee } from 'src/app/Model/Employee';
import { CongeServicesService } from 'src/app/Services/Conge/conge-services.service';
import { DatePipe } from '@angular/common';


@Component({
  templateUrl: './delegation.component.html',
  providers: [MessageService]
})
 
export class DelegationComponent implements OnInit {


  startDate: Date;
  endDate: Date;

  selectedEmployeeId: number;

  panelMenuItems: MenuItem[] = [];

  items: MenuItem[] = [];

  constructor(private congeServ: CongeServicesService, private datePipe: DatePipe, private messageService: MessageService) { }

  ngOnInit() {
    this.loadMenuItems();
  }


  loadMenuItems(): void {
    this.congeServ.getEmployeesDelegDev().subscribe(delegEmployees => {
      this.panelMenuItems.push({
        label: 'Développeurs',
        items: this.convertToMenuItems(delegEmployees),

      });
    });

    this.congeServ.getEmployeesDelegContr().subscribe(controleEmployees => {
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

  message: string;

  saveDelegation() {
    if (this.selectedEmployeeId) {
      const dateDebut = new Date(this.startDate);
      const dateFin = new Date(this.endDate);
      const formattedDate1 = this.datePipe.transform(dateDebut, "yyyy-MM-dd'T'HH:mm:ss");
      const formattedDate2 = this.datePipe.transform(dateFin, "yyyy-MM-dd'T'HH:mm:ss");
      console.log(formattedDate1)
      console.log(formattedDate2)
      this.congeServ.delegationRole(this.selectedEmployeeId, formattedDate1, formattedDate2).subscribe(response => {
        if (response.message==="OK") {
        console.log(response.message);
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Congé Annulé', life: 5000 });
        }
        else(response.message!="OK")
        {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: response.message , life: 3000 });
        }
      }
      );
    }
 }
}

