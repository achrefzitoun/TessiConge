import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Conge } from 'src/app/Model/Conge';
import { CongeServicesService } from 'src/app/Services/Conge/conge-services.service';
import { parseISO } from 'date-fns';
import { Employee } from 'src/app/Model/Employee';
import { DatePipe } from '@angular/common';


@Component({
  templateUrl: './view-all-conge.component.html',
  providers: [MessageService]
})
export class ViewAllCongeComponent implements OnInit {

  conges: Conge[] = [];
  congeEvents: any[] = [];

  employes: Employee[]
  employe: Employee;
  selecteConge: Conge
  congeMap: any;

  EmployeDialog: boolean = false;


  constructor(private congeserv: CongeServicesService, private datePipe: DatePipe) { }

  ngOnInit() {
    this.congeserv.getAllCongeAccepr().subscribe(data => {
      this.conges = data;
      this.congeMap = new Map();
      for (const conge of data) {
        const dateDebut = new Date(conge.dateDebut.replace(' ', 'T'));
        const dateFin = new Date(conge.dateFin.replace(' ', 'T'));
        const nomJour = conge.description;
        const dates = [];
        const d = new Date(dateDebut);
        while (d <= dateFin) {
          dates.push(new Date(d));
          d.setDate(d.getDate() + 1);
        }
        this.congeMap.set(nomJour, { dateDebut, dateFin, dates });
      }
    });
  }

  mapCongesToEvents(conges: Conge[]): any[] {
    return conges.map(conge => {
      const start = parseISO(conge.dateDebut);
      const end = parseISO(conge.dateFin);

      console.log(start)
      console.log(end)
      return {
        title: conge.demandeur ? conge.demandeur.nom : "Demandeur inconnu",
        start: start,
        end: end,
        date: start
      };
    });
  }

  getEventsForDate(date: Date): any[] {
    return this.congeEvents.filter(event => {
      return date >= event.start && date <= event.end;
    }) || [];
  }

  isDateDecorated(date: any): boolean {
    return this.congeMap &&
      Array.from(this.congeMap.values()).some((conge: any) =>
        conge.dates && conge.dates.some((d: Date) =>
          d.getDate() === date.day && d.getMonth() === date.month && d.getFullYear() === date.year
        )
      );
  }


  onDateSelect(date: Date) {
    let isDateInConges = false;

    for (let index = 0; index < this.conges.length; index++) {
      const c = this.conges[index];
      const parsedDateDebutConge = new Date(c.dateDebut);
      const parsedDateFinConge  = new Date(c.dateFin);
      if (date.getDate >= parsedDateDebutConge.getDate && date.getDate <= parsedDateFinConge.getDate ) {
        isDateInConges = true;
        break;
      }
    }
    console.log(isDateInConges)
    if (isDateInConges) {
      this.selecteConge = new Conge()
      const d = new Date(date);
      const formattedDate1 = this.datePipe.transform(d, "yyyy-MM-dd'T'HH:mm:ss");
      console.log(formattedDate1)
      this.congeserv.getEmplyesContainDate(formattedDate1)
        .subscribe(data => {
          this.employes = data;         
        });

        this.EmployeDialog = true
    }
   
  }


}

