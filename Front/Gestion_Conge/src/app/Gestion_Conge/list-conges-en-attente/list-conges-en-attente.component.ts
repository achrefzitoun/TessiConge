import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { Conge } from 'src/app/Model/Conge';
import { Employee } from 'src/app/Model/Employee';
import { Etat } from 'src/app/Model/Etat';
import { MotifRefus } from 'src/app/Model/MotifRefus';
import { TypeConge } from 'src/app/Model/TypeConge';
import { CongeServicesService } from 'src/app/Services/Conge/conge-services.service';

@Component({
  providers: [MessageService],
  templateUrl: './list-conges-en-attente.component.html',
})
export class ListCongesEnAttenteComponent implements OnInit {

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
  
  cols: any[] = [];

  constructor(private messageService: MessageService, private congeserv: CongeServicesService, private datePipe: DatePipe) { }

  ngOnInit() {

    this.congeserv.getAllCongesEnAttente().subscribe({
      next: (data) =>
        this.conges = data,
    });
  }


  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }

  getClassByEtat(etat: Etat): string {
    switch (etat) {
      case Etat.En_Attente:
        return 'status-lowstock';
      default:
        return '';
    }
  }

}
