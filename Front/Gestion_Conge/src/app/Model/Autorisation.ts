import { Employee } from "./Employee";

export class Autorisation {
    idAutorisation: number;
    dateDebut: string;
    dateFin: string;
    description : string;
    demandeur: Employee;
  }