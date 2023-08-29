import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Conge } from 'src/app/Model/Conge';
import { Employee } from 'src/app/Model/Employee';
import { Etat } from 'src/app/Model/Etat';
import { MotifRefus } from 'src/app/Model/MotifRefus';
import { TypeConge } from 'src/app/Model/TypeConge';
import { TypeMotif } from 'src/app/Model/TypeMotif';
import { CongeServicesService } from 'src/app/Services/Conge/conge-services.service';
import { MotifRefusService } from 'src/app/Services/MotifRefus/motif-refus.service';

@Component({
  templateUrl: './reponse-conge.component.html',
  providers: [MessageService]

})
export class ReponseCongeComponent {

  motifRefus: MotifRefus[];
  motifAutre: MotifRefus;
  motif: MotifRefus;
  typeConge: TypeConge
  id: number;
  conge!: Conge;
  demandeur!: Employee;

  etat: string[] = [
    'Accepte',
    'Refuse',
    'En_Attente'
  ];
  e : String;
  annule: String[] = ['Annule'];

  description : string = '';
  message: string;
  mess: boolean = false;

  constructor(private motifRefusServices: MotifRefusService, private messageService: MessageService, private route: ActivatedRoute, private congeServices: CongeServicesService) { }
  
  ngOnInit() {
    this.motif = new MotifRefus();
    this.motifAutre = new MotifRefus();
    this.motifAutre.description = 'Autre'
    this.motifAutre.typeMotif = TypeMotif.Autre;
    this.id = +this.route.snapshot.queryParamMap.get('id')!;
    console.log(this.id)
    //this.id = 90;
    this.congeServices.getCongeById(this.id).subscribe(conge => {
      this.conge = conge,
      this.demandeur = conge.demandeur,
      this.typeConge = conge.typeConge
    })
    this.motifRefusServices.getAllMotifs().subscribe(data => {
      this.motifRefus = data,
      this.motifRefus.push(this.motifAutre)
    });
  }


  save() {
    if(this.conge.etat==Etat.Accepte){
      this.motif=null
      this.e = this.conge.etat
      this.congeServices.reponseConge(this.conge.idConge,this.e,this.motif).subscribe(
        response => {
          this.message = response.message;
          this.mess = true;
        },
        error => {
          this.message = error.message;
        }
      );
    }
    else{
      this.e = this.conge.etat
      this.motif.description = this.description
      this.congeServices.reponseConge(this.conge.idConge,this.e,this.motif).subscribe(
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
