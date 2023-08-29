import { Employee } from "./Employee";
import { MotifRefus } from "./MotifRefus";
import { TypeConge } from "./TypeConge";

export class Conge {
    idConge: number;
    dateDemande: string;
    dateDebut: string;
    dateFin: string;
    duree: number;
    etat: string;
    description: string;
    pieceJointe: string;
    idDelegue: number;
    dateValidation: string;
    typeConge: TypeConge;
    demandeur: Employee;
    validateur: Employee;
    motifRefus: MotifRefus;
  }