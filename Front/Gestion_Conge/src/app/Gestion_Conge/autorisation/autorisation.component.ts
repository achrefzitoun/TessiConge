import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Autorisation } from 'src/app/Model/Autorisation';
import { JourFerie } from 'src/app/Model/JourFerie';
import { CongeServicesService } from 'src/app/Services/Conge/conge-services.service';
import { JourFerieService } from 'src/app/Services/JourFerie/jour-ferie.service';

@Component({
  templateUrl: './autorisation.component.html',
  providers: [MessageService]
})
export class AutorisationComponent {

  autorisation: Autorisation;
  joursFeriesMap: any;
  joursFeries: JourFerie[] = [];
  date: Date;
  message: string;
  mess: boolean = false;


  constructor(private jourFerieServices: JourFerieService, private datePipe: DatePipe, private congeServices: CongeServicesService) { }

  ngOnInit() {
    this.date = new Date();
    this.autorisation = new Autorisation();

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

  save() {
    console.log("test")
    var dateDebut = new Date(this.date);
    const formattedDate = this.datePipe.transform(dateDebut, "yyyy-MM-dd'T'HH:mm:ss");
    this.autorisation.dateDebut = formattedDate;
    this.congeServices.getAutorisation(this.autorisation).subscribe(
      response => {
        this.message = response.message;
        this.mess = true;
      });

  }


}
