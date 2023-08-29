import { Conge } from "./Conge";
import { NatureType } from "./NatureType";

export class TypeConge {
  idTypeConge: number;
  nomType: string;
  description: string;
  natureType: NatureType;
  nbrJours: number;
  listConge: Conge[];
}