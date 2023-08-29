import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { JourFerie } from 'src/app/Model/JourFerie';
import { Politique } from 'src/app/Model/Politique';
import { JourFerieService } from 'src/app/Services/JourFerie/jour-ferie.service';

@Component({
  templateUrl: './gestion-jour-ferie.component.html',
  providers: [MessageService]
})

export class GestionJourFerieComponent {
  dates: Date[];
  joursFeries: JourFerie[] = [];
  id: number;
  date: Date
  selectedDate: Date;
  editDialog: boolean = false;
  addDialog: boolean = false;
  message: string;
  mess: boolean = false;
  politique!: Politique;
  politiques: Politique[] = [];
  jourFerie: JourFerie;
  date1: Date;
  date2: Date;
  joursFeriesMap: any;

  selectedJ: JourFerie;
  selectedJPolitiques: Politique[];
  display: boolean = false;
  selectedPolitiques: Politique[];
  newJourFerie: JourFerie;
  newJourFeriePolitiques: Politique[]

  constructor(private router: Router, private jourFerieServices: JourFerieService, private datePipe: DatePipe, private messageService: MessageService, private route : ActivatedRoute) { }

  ngOnInit() {
    this.jourFerie = new JourFerie;

    this.politique = new Politique()
    this.id = +this.route.snapshot.queryParamMap.get('id')!;

    this.jourFerieServices.retrievePolitique(this.id).subscribe(politique => {
      this.politique = politique;
      if(politique==null){
        console.log("hh")
        this.router.navigateByUrl('/notfound');
        } 
    })

    this.jourFerieServices.getJourFerieByPolitique(this.id).subscribe(data => {
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



  onDateSelect(date: Date) {
    let isDateInJoursFeries = false;

    for (let index = 0; index < this.joursFeries.length; index++) {
      const jourFerie = this.joursFeries[index];
      const parsedDateDebutJourFerie = new Date(jourFerie.dateDebut);
      const parsedDateFinJourFerie = new Date(jourFerie.dateFin);
      if (date >= parsedDateDebutJourFerie && date <= parsedDateFinJourFerie) {
        isDateInJoursFeries = true;
        break;
      }
    }
    if (isDateInJoursFeries) {
      this.selectedJ = new JourFerie()
      const d = new Date(date);
      const formattedDate1 = this.datePipe.transform(d, "yyyy-MM-dd'T'HH:mm:ss");
      this.jourFerieServices.getJourFerieByDate(formattedDate1).subscribe(dat => {
        this.selectedJ = dat;
        this.selectedJ.politiques = dat.politiques;
        this.date1 = new Date(dat.dateDebut);
        this.date2 = new Date(dat.dateFin);
        this.jourFerieServices.retrieveAllPoitiquesbyJourFerie(dat.idJour).subscribe(pol => this.politiques = pol)
      });

      this.editDialog = true;
    }
  }

  hideEditDialog() {
    this.selectedJ = null;
    this.date1 = null;
    this.date2 = null;
    this.editDialog = false;
  }

  saveEdit() {
    const dateDebut = new Date(this.date1);
    const dateFin = new Date(this.date2);
    const formattedDate1 = this.datePipe.transform(dateDebut, "yyyy-MM-dd'T'HH:mm:ss");
    const formattedDate2 = this.datePipe.transform(dateFin, "yyyy-MM-dd'T'HH:mm:ss");
    this.selectedJ.dateDebut = formattedDate1;
    this.selectedJ.dateFin = formattedDate2;
    this.jourFerieServices.updateJourFerie(this.selectedJ).subscribe();
    for (let index = 0; index < this.politiques.length; index++) {
      this.jourFerieServices.assignJourFeriePolitiques(this.politiques[index].idPolitique, this.selectedJ.idJour).subscribe(/*p => window.location.reload()*/);
    }
  }


  openNew() {
    this.jourFerieServices.retrieveAllPolitique().subscribe(data => this.selectedPolitiques = data)
    this.newJourFerie = new JourFerie;
    this.date1 = null;
    this.date2 = null;
    this.addDialog = true;
  }

  saveNew() {
    const dateDebut = new Date(this.date1);
    const dateFin = new Date(this.date2);
    const formattedDate1 = this.datePipe.transform(dateDebut, "yyyy-MM-dd'T'HH:mm:ss");
    const formattedDate2 = this.datePipe.transform(dateFin, "yyyy-MM-dd'T'HH:mm:ss");
    this.newJourFerie.dateDebut = formattedDate1;
    this.newJourFerie.dateFin = formattedDate2;
    var i: number;
    var s: string;
    for (let index = 0; index < this.joursFeries.length; index++) {
      const jourFerie = this.joursFeries[index];
      const parsedDateDebutJourFerie = new Date(jourFerie.dateDebut);
      const parsedDateFinJourFerie = new Date(jourFerie.dateFin);
      if (
        (dateDebut >= parsedDateDebutJourFerie && dateDebut <= parsedDateFinJourFerie) ||
        (dateFin >= parsedDateDebutJourFerie && dateFin <= parsedDateFinJourFerie) ||
        (dateDebut <= parsedDateDebutJourFerie && dateFin >= parsedDateFinJourFerie)
      ) {
        if (dateDebut >= parsedDateDebutJourFerie && dateFin <= parsedDateFinJourFerie) {
          i = 1;
          s = "La période est entièrement incluse dans le jour férié : " + jourFerie.nomDate;
        } else if (dateDebut <= parsedDateDebutJourFerie && dateFin >= parsedDateFinJourFerie) {
          i = 1;
          s = "La période englobe complètement le jour férié : " + jourFerie.nomDate;
        } else {
          i = 1;
          s = "La période chevauche partiellement le jour férié : " + jourFerie.nomDate;
        }
      }
    }

    if (i != 1) {
      this.jourFerieServices.addJourFerie(this.newJourFerie).subscribe(p => {
        for (let index = 0; index < this.newJourFeriePolitiques.length; index++) {
          this.jourFerieServices.assignJourFeriePolitiques(this.newJourFeriePolitiques[index].idPolitique, p.idJour).subscribe(p => window.location.reload());
        }
      });

    }
    else {
      this.messageService.add({ severity: 'error', summary: 'Erreur', detail: s, life: 3000 });
    }



  }

}
