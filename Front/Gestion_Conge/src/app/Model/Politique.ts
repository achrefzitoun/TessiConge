import { Employee } from "./Employee";
import { JourFerie } from "./JourFerie";
import { TypeConge } from "./TypeConge";

export class Politique {
    idPolitique: number;
    nomPolitique: string;
    descriptionPolitique: string;
    nombreJourOuvrable: number;
    jourFerie: JourFerie[];
    employees: Employee[];
    typeConge: TypeConge[];
  }