import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/demo/api/product';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { Conge } from 'src/app/Model/Conge';
import { CongeServicesService } from 'src/app/Services/Conge/conge-services.service';
import { TypeConge } from 'src/app/Model/TypeConge';
import { Employee } from 'src/app/Model/Employee';
import { MotifRefus } from 'src/app/Model/MotifRefus';
import { Etat } from 'src/app/Model/Etat';
import { NatureType } from 'src/app/Model/NatureType';
import { DatePipe } from '@angular/common';
import { DialogService } from 'primeng/dynamicdialog';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { JourFerie } from 'src/app/Model/JourFerie';




@Component({
  providers: [MessageService],
  templateUrl: './list-demande.component.html'
})
export class ListDemandeComponent implements OnInit {
  congeDialog: boolean = false;

  deleteCongeDialog: boolean = false;

  deleteCongesDialog: boolean = false;

  pdfDialog: boolean = false;

  editDialog: boolean = false
  dates!: string;
  datef!: string;
  idtype: number;
  typesConges: TypeConge[];

  file!: File;


  conges: Conge[];

  valNature: string;

  natureConge: string[] = [
    'Speciale',
    'Normale'
  ];


  startDate: Date = new Date();
  endDate: Date;
  idType: number;
  idTypeConge: number;
  calculatedDuration: number;


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

  duree: number;

  type: TypeConge

  selectedConges: Product[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];

  rowsPerPageOptions = [5, 10, 20];

  totalConge: any;

  uploadedFiles: any[] = [];

  calendarLocaleOptions: any = {
    dateFormat: 'dd/mm/yy'
  };

  types: TypeConge[];
  selectedType: TypeConge | undefined;

  joursFeriesMap: any;
  joursFeries: JourFerie[] = [];


  constructor(private congeserv: CongeServicesService, private messageService: MessageService, private datePipe: DatePipe, private sanitizer: DomSanitizer) { }
  typeConge: Partial<TypeConge>;

  ngOnInit() {

    this.valNature = 'Normale';

    this.congeserv.getCongesByEmploye().subscribe({
      next: (data) =>
        this.conges = data,
    });
  }

  openNew() {
    this.type = new TypeConge();
    this.conge = new Conge;
    this.congeserv.getTypeCongebyNature(NatureType.Normale).subscribe(data => this.typesConges = data)
    this.valNature = 'Normale';
    this.submitted = false;
    this.congeDialog = true;
/*
//Recuperation d'employee connectée : emp.politique.id
    this.jourFerieServices.getJourFerieByPolitique(1).subscribe(data => {
      this.joursFeries = data;
      this.joursFeriesMap = new Map();
      for (const jourFerie of data) {
        const dateDebut = new Date(jourFerie.dateDebut.replace(' ', 'T'));
        const dateFin = new Date(jourFerie.dateFin.replace(' ', 'T'));
        const nomJour = jourFerie.nomDate;
        const dates = [];
        const d = new Date(dateDebut);
        while (d <= dateFin) {
          dates.push(new Date(d));
          d.setDate(d.getDate() + 1);
        }
        this.joursFeriesMap.set(nomJour, { dateDebut, dateFin, dates });
      }
    });*/
  }



  getType() {
    var natureCongeValue: NatureType = NatureType[this.valNature as keyof typeof NatureType];
    this.congeserv.getTypeCongebyNature(natureCongeValue).subscribe(data => this.typesConges = data)

  }

  deleteSelectedProducts() {
    this.deleteCongesDialog = true;
  }

  editConge(conge: Conge) {
    this.congeserv.getTypeCongebyNature(conge.typeConge.natureType).subscribe(data => {
      this.typesConges = data;
      const selectedTypeIndex = this.typesConges.findIndex(type => type.idTypeConge === conge.typeConge.idTypeConge);
      this.type = this.typesConges[selectedTypeIndex]
      if (selectedTypeIndex !== -1) {
        const selectedType = this.typesConges[selectedTypeIndex];
        const firstType = { ...this.typesConges[0] };
        this.typesConges[0] = selectedType;
        this.typesConges[selectedTypeIndex] = firstType;
      }
    });
    this.conge = conge
    this.valNature = conge.typeConge.natureType
    this.editDialog = true;
    const dateObject = new Date(conge.dateDebut);
    this.startDate = dateObject;
    const dateObject2 = new Date(conge.dateFin);
    this.endDate = dateObject2;
    this.calculatedDuration = conge.duree;

  }

  deleteConge(id: number) {
    this.deleteCongeDialog = true;
    this.congeserv.getOneConge(id).subscribe(
      (data: Conge) => {
        this.conge = data;
      })
  }

  confirmDeleteSelected(id: number) {
    this.deleteCongesDialog = false;
    this.congeserv.annulConge(id).subscribe((data: Conge) => {
      this.conge = data;
      this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Congé Annulé avec succes', life: 3000 });
      window.location.reload();
    });
   
    this.selectedConges = [];

  }

  confirmDelete(id: number) {
    this.deleteCongeDialog = false;
    this.congeserv.annulConge(id).subscribe();
    window.location.reload();
    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Congé Annulé', life: 5000 });

  }

  hideDialog() {
    this.conge = null;
    this.congeDialog = false;
    this.editDialog = false;
    this.valNature = null;
    this.startDate = null;
    this.endDate = null;
    this.typesConges = null;
    this.submitted = false;
    this.calculatedDuration = 0;
  }

  message: string;
  saveConge() {
    this.submitted = true;

    //origine 
    const originalStartDate = this.conge.dateDebut;
    const originalEndDate = this.conge.dateFin;


    const dateDebut = new Date(this.startDate);
    const dateFin = new Date(this.endDate);
    const formattedDate1 = this.datePipe.transform(dateDebut, "yyyy-MM-dd'T'HH:mm:ss");

    this.conge.dateDebut = formattedDate1;

    if (this.conge.idConge != null) {
      const formattedDate2 = this.datePipe.transform(dateFin, "yyyy-MM-dd'T'HH:mm:ss");
      this.conge.dateFin = formattedDate2;

      const datesModified = originalStartDate !== formattedDate1 || originalEndDate !== formattedDate2;
      if (datesModified) {
        if (this.type.natureType != 'Normale') {
          const daterrr1 = new Date(this.conge.dateDebut);
          const d1 = new Date(daterrr1);
          d1.setDate(d1.getDate() + this.type.nbrJours);
          const formattedEndDate2 = this.datePipe.transform(d1, "yyyy-MM-dd'T'HH:mm:ss");
          this.conge.dateFin = formattedEndDate2;
          this.congeserv.updateConge(this.conge, this.file, this.type.idTypeConge, formattedDate1, formattedEndDate2).subscribe();
          this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Congé modifié', life: 3000 });
          window.location.reload();

        }
        else {
          if (this.endDate) {
            this.congeserv.updateConge(this.conge, this.file, this.type.idTypeConge, formattedDate1, formattedDate2).subscribe();
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Congé modifié ', life: 3000 });
            window.location.reload();

          }
        }
      }
      else {
        this.congeserv.updateConge(this.conge, this.file, this.type.idTypeConge, formattedDate1, formattedDate2).subscribe();
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Congé modifié ', life: 3000 });       
        window.location.reload();
      }

    }
    else if (this.conge.idConge == null) {
      if (!this.endDate) {
        if (this.type.nomType == 'Demi jour matin') {
          dateDebut.setHours(8);
          const f = new Date(dateDebut);
          f.setHours(12);
          const formattedDate2 = this.datePipe.transform(f, "yyyy-MM-dd'T'HH:mm:ss");
          this.conge.dateFin = formattedDate2;
          this.congeserv.addConge(this.conge, this.file, this.type.idTypeConge, formattedDate1, formattedDate2).subscribe();

          console.log(this.conge.dateDebut)
          console.log(this.conge.dateFin)
        }
        else if (this.type.nomType == 'Demi jour après midi') {
          dateDebut.setHours(13);
          const f = new Date(dateDebut);
          f.setHours(17);
          const formattedDate2 = this.datePipe.transform(f, "yyyy-MM-dd'T'HH:mm:ss");
          this.conge.dateFin = formattedDate2;

          this.congeserv.addConge(this.conge, this.file, this.type.idTypeConge, formattedDate1, formattedDate2).subscribe();

          console.log(this.conge.dateDebut)
          console.log(this.conge.dateFin)
        }

        const daterrr = new Date(this.conge.dateDebut);
        const d = new Date(daterrr);
        d.setDate(d.getDate() + this.type.nbrJours);
        const formattedEndDate = this.datePipe.transform(d, "yyyy-MM-dd'T'HH:mm:ss");
        this.conge.dateFin = formattedEndDate;
        this.congeserv.addConge(this.conge, this.file, this.type.idTypeConge, formattedDate1, formattedEndDate).subscribe(response => {
          this.message = response.message;
          console.log(response.message)
          if (response.message == "OK") {
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'CONGE AJOUTE AVEC SUCCEE ', life: 3000 });
            window.location.reload();
          }
          else if (response.message != "OK") {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Echec ', life: 3000 });
          }

        });

      }
      else {
        if (this.endDate) {
          const formattedDate2 = this.datePipe.transform(dateFin, "yyyy-MM-dd'T'HH:mm:ss");
          this.conge.dateFin = formattedDate2;
          this.congeserv.addConge(this.conge, this.file, this.type.idTypeConge, formattedDate1, formattedDate2).subscribe(
            response => {
              this.message = response.message;
              if (response.message == 'OK') {
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'CONGE AJOUTE AVEC SUCCEE ', life: 3000 });
                window.location.reload();
              }
              else if (response.message != 'OK') {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Echec ', life: 3000 });
              }
            });

        }
      }
    }
    this.editDialog = false;
    this.congeDialog = false;
    this.startDate = null
    this.valNature = null
    this.type = null;
    this.typesConges = null;
    this.conge = null

  }


  calculateDuration() {
    const s = this.datePipe.transform(this.startDate, "yyyy-MM-dd'T'HH:mm:ss");

    if (this.type && this.startDate) {
      if (this.endDate && this.type.natureType == NatureType.Normale  ||  this.type.nomType == 'Congé de maladie') {
        const f = this.datePipe.transform(this.endDate, "yyyy-MM-dd'T'HH:mm:ss");
        this.congeserv.getDurationN(s, f).subscribe(duration => {
          this.calculatedDuration = duration;
          this.updateEndDate();
        });
      } 
      else if (this.type.natureType == NatureType.Speciale && this.type.nomType != 'Congé de maladie') {
        this.congeserv.getDurationS(s, this.type.idTypeConge).subscribe(duration => {
          this.calculatedDuration = duration;
          this.updateEndDate();
        });;
      }
      else if (this.type.nomType == 'Demi jour après midi' || this.type.nomType == 'Demi jour matin' ) {
        this.updateEndDate();
      }
    } else {
      this.calculatedDuration = 0;
    }
  }

  updateEndDate() {
    console.log("test")
    if (this.startDate && this.calculatedDuration) {
      const endDate = new Date(this.startDate);
      endDate.setDate(endDate.getDate() + this.calculatedDuration);
      this.endDate = endDate;
    }
    else if (this.type.nomType == 'Demi jour après midi' || this.type.nomType == 'Demi jour matin' ) {
      if (this.type.nomType == 'Demi jour après midi'){
        const dates = this.startDate.setHours(13);
          const f = new Date(dates);
          f.setHours(17);
          this.endDate = f;
      }
      else {
        const dates = this.startDate.setHours(8);
          const f = new Date(dates);
          f.setHours(12);
          this.endDate = f;
      }
    }
   
  }

  onFileSelected(event: any) {
    this.file = event.files[0];
  }


  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }

  isDateDecorated(date: any): boolean {
    return this.joursFeriesMap &&
      Array.from(this.joursFeriesMap.values()).some((jourFerie: any) =>
        jourFerie.dates && jourFerie.dates.some((d: Date) =>
          d.getDate() === date.day && d.getMonth() === date.month && d.getFullYear() === date.year
        )
      );
  }

  getClassByEtat(etat: Etat): string {
    switch (etat) {
      case Etat.Accepte:
        return 'status-instock';
      case Etat.En_Attente:
        return 'status-lowstock';
      case Etat.Annule:
        return 'status-outofstock';
      case Etat.Refuse:
        return 'status-renewal';
      default:
        return '';
    }
  }

  onUpload(event: any) {
    for (const file of event.files) {
      this.uploadedFiles.push(file);
    }

    this.messageService.add({ severity: 'info', summary: 'Success', detail: 'File Uploaded' });
  }



  getAllTypesConge(): void {
    this.congeserv.getAllType().subscribe(
      (types) => {
        this.types = types;
      },
    );
  }



  exportConge() {
    this.congeserv.exportCongeExcel().subscribe(response => {
      const blob = new Blob([response.body], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'Liste_Des_Conges.xlsx';
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  pdfUrl!: SafeResourceUrl;

  displayPdf(): void {
    this.congeserv.getGeneratedPdf().subscribe(
      (data: any) => {
        const pdfBlob = new Blob([data], { type: 'application/pdf' });
        this.pdfUrl = this.sanitizer.bypassSecurityTrustResourceUrl(URL.createObjectURL(pdfBlob));

        this.pdfDialog = true; // Afficher la boîte de dialogue
      },
      error => {
        console.error('Une erreur s\'est produite lors de la récupération du PDF :', error);
      }
    );

    this.submitted = false;
  }



  

}
