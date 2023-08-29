import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/demo/api/product';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { ProductService } from 'src/app/demo/service/product.service';
import { TypeMotif } from 'src/app/Model/TypeMotif';
import { Politique } from 'src/app/Model/Politique';
import { MotifsService } from 'src/app/Services/Motifs/motifs.service';
import { PolitiqueService } from 'src/app/Services/Politique/politique.service';
import { Employee } from 'src/app/Model/Employee';

@Component({
  templateUrl: './politique.component.html',
  providers: [MessageService]
})
export class PolitiqueComponent implements OnInit {

  politiqueDialog: boolean = false;

  deletePolitiqueDialog: boolean = false;

  deletePolitiquesDialog: boolean = false;

  politiques: Politique[] = [];

  politique: Politique;

  selectedPolitiques: Product[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];

  employes: Employee[] = [];

  politiqueId: number

  rowsPerPageOptions = [5, 10, 20];

  constructor(private messageService: MessageService, private politiqueserv: PolitiqueService) { }

  employesParPolitique: Map<number, Employee[]> = new Map<number, Employee[]>();

  selectedEmployee: Employee;


  ngOnInit() {
    

    this.politiqueserv.getPolitique().subscribe({
      next: (data) => {
        this.politiques = data;

        // Initialisez un Map vide pour stocker les employés par politique
        const tempEmployesParPolitique: Map<number, Employee[]> = new Map<number, Employee[]>();

        // Pour chaque politique, récupérez les employés et stockez-les dans le Map temporaire
        for (const politique of this.politiques) {
          this.politiqueserv.getEmployeeByPolitique(politique.idPolitique).subscribe({
            next: (employees) => {
              tempEmployesParPolitique.set(politique.idPolitique, employees);

              // Si tous les employés ont été récupérés, mettez à jour employesParPolitique
              if (tempEmployesParPolitique.size === this.politiques.length) {
                this.employesParPolitique = tempEmployesParPolitique;
              }
            },
          });
        }
      },
    });
  }

  getEmployeeDropdownOptions(politiqueId: number) {
    const employeesForPolitique = this.employesParPolitique.get(politiqueId);

    // Créez les options pour le dropdown en utilisant les noms d'employés associés à la politique sélectionnée
    return employeesForPolitique.map((employee) => {
      return { label: employee.nom, value: employee.nom };
    });
  }


  openNew() {
    this.politique = new Politique()
    this.submitted = false;
    this.politiqueDialog = true;
  }

  deleteSelectedPolitiques() {
    this.deletePolitiquesDialog = true;
  }

  editPolitique(politique: Politique) {
    this.politique = { ...politique };
    this.politiqueDialog = true;
  }

  deletePolitique(id: number) {
    this.deletePolitiqueDialog = true;
    this.politiqueserv.getPolitiqueById(id).subscribe(
      (data: Politique) => {
        this.politique = data;
      });

  }

  confirmDeleteSelected() {
    this.deletePolitiquesDialog = false;
    //this.politiques = this.politiques.filter(val => !this.selectedPolitiques.includes(val));
    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
    this.selectedPolitiques = [];
  }

  confirmDelete(id: number) {
    this.deletePolitiqueDialog = false;
    this.politiqueserv.deletePolitique(id).subscribe();
    window.location.reload();
    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Congé Annulé', life: 5000 });
  }

  hideDialog() {
    this.politiqueDialog = false;
    this.submitted = false;
    this.politique = null
  }

  savePolitique() {
    this.submitted = true;

    if (this.politique.idPolitique != null) {

      this.politiqueserv.updatePolitique(this.politique).subscribe(() => {
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Motif Created', life: 3000 });
      });
    }
    else {
      this.politiqueserv.addPolitique(this.politique).subscribe(() => {
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Motif Created', life: 3000 });
      });
    }


    this.politiques = [...this.politiques];
    this.politiqueDialog = false;
    this.politique = new Politique;

  }

  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }


}
