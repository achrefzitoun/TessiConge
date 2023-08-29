import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { JourFerieService } from 'src/app/Services/JourFerie/jour-ferie.service';
import { DatePipe } from '@angular/common';
import { Table } from 'primeng/table';
import { TypeConge } from 'src/app/Model/TypeConge';
import { TypeCongeService } from 'src/app/Services/TypesConges/type-conge.service';
import { Politique } from 'src/app/Model/Politique';
import { NatureType } from 'src/app/Model/NatureType';
import { CongeServicesService } from 'src/app/Services/Conge/conge-services.service';

@Component({
  templateUrl: './gestion-de-type-conge.component.html',
  providers: [MessageService]
})
export class GestionDeTypeCongeComponent {

  types: TypeConge[];
  type: TypeConge;
  cols: any[] = [];
  selectedTypes: TypeConge[];
  submitted: boolean = false;
  politiques: Politique[] = [];
  typeDialoge: boolean = false;
  natureType: string[] = [
    'Speciale',
    'Normale'
  ];
  valNature: string;
  nature: NatureType;
  selectedPolitiques: Politique[] = [];
  deleteTypeDialog: boolean = false;

  test:Politique[] = [];


  constructor(private congeService: CongeServicesService, private jourFerieServices: JourFerieService, private datePipe: DatePipe, private messageService: MessageService, private typeCongeService: TypeCongeService) { }

  ngOnInit() {
    this.congeService.retrieveAllTypeConge().subscribe(data => this.types = data)
    this.jourFerieServices.retrieveAllPolitique().subscribe(data => this.selectedPolitiques = data)

  }

  openNew() {
    if(this.typeDialoge = false){
      this.politiques = null;
    }
    this.type = new TypeConge;
    this.submitted = false;
    this.typeDialoge = true;
  }

  editType(type: TypeConge) {
    this.type = type;
    this.submitted = false;
    this.typeCongeService.retrieveAllPolitiquesByType(type.idTypeConge).subscribe(data => {
      this.politiques = data;
    })
    this.typeDialoge = true;
  }


  saveEditType(){
    this.submitted = true;
    this.typeCongeService.editTypeConge(this.type, this.politiques)
      .subscribe(editedTypeConge => {
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Type de conge modifier avec succçes', life: 3000 });
      });

    this.typeDialoge = false;
    this.type = null;
    this.politiques = null;
  }

  deleteType(type: TypeConge) {
    this.deleteTypeDialog = true;
    this.type = { ...type };
  }

  confirmDelete() {
    this.deleteTypeDialog = false;
    this.typeCongeService.deleteTypeConge(this.type.idTypeConge).subscribe();
    this.types = this.types.filter(val => val.idTypeConge !== this.type.idTypeConge);
    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Type supprimer', life: 3000 });
    this.type = null;
  }

  getNature() {
    this.nature = NatureType[this.valNature as keyof typeof NatureType];
  }

  saveType(): void {
    this.submitted = true;
    this.typeCongeService.addTypeConge(this.type, this.politiques)
      .subscribe(newTypeConge => {
        this.types.push(newTypeConge);
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Type de conge ajouter avec succçes', life: 3000 });
      });
    this.types = [...this.types];
    this.typeDialoge = false;
    this.type = null;
    this.politiques = null;
  }

  hideDialog(){
    this.type = null;
    this.typeDialoge = false;
    this.politiques = null;
  }

  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }

}
