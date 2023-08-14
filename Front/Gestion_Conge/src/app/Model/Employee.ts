import { Conge } from "./Conge";
import { Politique } from "./Politique";
import { Role } from "./Role";

export class Employee {
    idEmp?: string;
    email?: string;
    username?: string;
    motDePass?: string;
    nom?: string;
    prenom?: string;
    adresse?: string;
    soldeConge?: string;
    statut?: string;
    entite?: string;
    role?: Role;
    superviseur?: Employee;
    listDemande?: Conge[];
    listValidation?: Conge[];
    politique?: Politique;
  }