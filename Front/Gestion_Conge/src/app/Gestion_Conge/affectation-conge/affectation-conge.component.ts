import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Conge } from 'src/app/Model/Conge';
import { Employee } from 'src/app/Model/Employee';
import { NatureType } from 'src/app/Model/NatureType';
import { TypeConge } from 'src/app/Model/TypeConge';
import { CongeServicesService } from 'src/app/Services/Conge/conge-services.service';
import { DatePipe } from '@angular/common';
import { JourFerie } from 'src/app/Model/JourFerie';
import { JourFerieService } from 'src/app/Services/JourFerie/jour-ferie.service';
import { em } from '@fullcalendar/core/internal-common';

@Component({
  templateUrl: './affectation-conge.component.html',
  providers: [MessageService]
})

export class AffectationCongeComponent {
  typesConges: TypeConge[];
  typeConge: TypeConge;
  filteredEmployees: any[] = [];
  filteredTypesConges: any[] = [];
  employees: Employee[] = [];
  sl: any[] = [];
  employee: Employee;
  conge: Conge;
  natureConge: string[] = [
    'Speciale',
    'Normale'
  ];

  valNature: string;

  calendarLocaleOptions: any = {
    dateFormat: 'dd/mm/yy'
  };
  file!: File;
  display: boolean = false;
  message: string;
  mess: boolean = false;

  date1: Date;
  date2: Date;
  // file !: File;


  joursFeriesMap: any;
  joursFeries: JourFerie[] = [];

  constructor(private jourFerieServices: JourFerieService, private congeServices: CongeServicesService, private messageService: MessageService, private datePipe: DatePipe) { }

  ngOnInit() {
    this.typeConge = new TypeConge;
    this.employee = new Employee;
    this.conge = new Conge;
    this.congeServices.getAllEmployees().subscribe(data => {
      this.employees = data;
      for (let i = 0; i < this.employees.length; i++) {
        this.employees[i].nomComplete = `${this.employees[i].nom} ${this.employees[i].prenom}`;
      }
    });
    
  }


  isDateDecorated(date: any): boolean {
    return this.joursFeriesMap &&
      Array.from(this.joursFeriesMap.values()).some((jourFerie: any) =>
        jourFerie.dates && jourFerie.dates.some((d: Date) =>
          d.getDate() === date.day && d.getMonth() === date.month && d.getFullYear() === date.year
        )
      );
  }

  filterEmployee(event: any) {
    const filtered: Employee[] = [];
    const query = event.query.toLowerCase();
    for (let i = 0; i < this.employees.length; i++) {
      const employee = this.employees[i];
      if (employee.nom.toLowerCase().indexOf(query) === 0 || employee.prenom.toLowerCase().indexOf(query) === 0) {
        filtered.push(employee);
      }
    }
    this.filteredEmployees = filtered;
  }


  filterTypeConge(event: any) {
    const filtered: TypeConge[] = [];
    const query = event.query.toLowerCase();
    for (let i = 0; i < this.typesConges.length; i++) {
      const tc = this.typesConges[i];
      if (tc.nomType.toLowerCase().indexOf(query) === 0) {
        filtered.push(tc);
      }
    }
    this.filteredEmployees = filtered;
  }

  getType() {
    var natureCongeValue: NatureType = NatureType[this.valNature as keyof typeof NatureType];
    setTimeout(() => {
      this.congeServices.getTypeCongebyNatures(natureCongeValue).subscribe(data => this.typesConges = data);
    });
    
    this.jourFerieServices.getJourFerieByPolitique(this.employee.politique.idPolitique).subscribe(data => {
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
    });
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  save() {
    var dateDebut = new Date(this.date1);
    var dateFin = new Date(this.date2);
    const formattedDate1 = this.datePipe.transform(dateDebut, "yyyy-MM-dd'T'HH:mm:ss");
    if (this.date2) {
      const formattedDate2 = this.datePipe.transform(dateFin, "yyyy-MM-dd'T'HH:mm:ss");
      this.conge.dateFin = formattedDate2;
    }
    this.conge.dateDebut = formattedDate1;

    if (this.typeConge.description == 'Maladie' && this.file == null) {
      this.message = "Donner une certificat medicale !";
      this.mess = true;
    }
    else {
      if (this.typeConge.nomType == 'Demi jour matin') {
        dateDebut.setHours(8);
        const f = new Date(dateDebut);
        f.setHours(12);
        const formattedDate1 = this.datePipe.transform(dateDebut, "yyyy-MM-dd'T'HH:mm:ss");
        const formattedDate2 = this.datePipe.transform(f, "yyyy-MM-dd'T'HH:mm:ss");
        this.conge.dateDebut = formattedDate1;
        this.conge.dateFin = formattedDate2;
        console.log(this.conge.dateDebut)
        console.log(this.conge.dateFin)
      }
      if (this.typeConge.nomType == 'Demi jour après midi') {
        dateDebut.setHours(13);
        const f = new Date(dateDebut);
        f.setHours(17);
        const formattedDate1 = this.datePipe.transform(dateDebut, "yyyy-MM-dd'T'HH:mm:ss");
        const formattedDate2 = this.datePipe.transform(f, "yyyy-MM-dd'T'HH:mm:ss");
        this.conge.dateDebut = formattedDate1;
        this.conge.dateFin = formattedDate2;
        console.log(this.conge.dateDebut)
        console.log(this.conge.dateFin)
      }
      
      

      if (this.typeConge.nomType == "Conge de paternite" || this.typeConge.nomType == "Conge de maternite" || this.typeConge.nomType == "Conge de mariage") {
        const daterrr = new Date(this.conge.dateDebut);
        const d = new Date(daterrr);
        d.setDate(d.getDate() + this.typeConge.nbrJours);
        const formattedEndDate = this.datePipe.transform(d, "yyyy-MM-dd'T'HH:mm:ss");
        this.conge.dateFin = formattedEndDate;
      }
      this.congeServices.affecterConge(this.employee.idEmp, this.typeConge.idTypeConge, this.conge, this.file).subscribe(
        response => {
          this.message = response.message;
          this.mess = true;
        },
        error => {
          this.message = error.message;
        }
      );
    }
  }

}
