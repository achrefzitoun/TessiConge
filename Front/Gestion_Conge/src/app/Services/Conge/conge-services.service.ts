import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Conge } from 'src/app/Model/Conge';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/Model/Employee';
import { TypeConge } from 'src/app/Model/TypeConge';
import { NatureType } from 'src/app/Model/NatureType';
import { MotifRefus } from 'src/app/Model/MotifRefus';
import { Autorisation } from 'src/app/Model/Autorisation';

@Injectable({
  providedIn: 'root'
})
export class CongeServicesService {

  private apiUrl = 'http://localhost:8081/Tessi/conge';

  constructor(private http: HttpClient) { }

  getAllEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.apiUrl}/GetAllEmplyees`);
  }

  getTypeCongebyNature(natureType: NatureType): Observable<TypeConge[]> {
    return this.http.get<TypeConge[]>(`${this.apiUrl}/getTypeCongebyNature/${natureType}`);
  }

  retrieveAllTypeConge(): Observable<TypeConge[]> {
    return this.http.get<TypeConge[]>(`${this.apiUrl}/retrieveAllTypeConge`);
  }

  affecterConge(idDemandeur: number, idTypeConge: number, conge: Conge, file: File | null): Observable<any> {
    const formData = new FormData();
    if (file) {
      formData.append('file', file);
    }
    formData.append('conge', JSON.stringify(conge));
    formData.append('idTypeConge', JSON.stringify(idTypeConge));
    formData.append('idDemandeur', JSON.stringify(idDemandeur));
    return this.http.put<any>(`${this.apiUrl}/affectationConge`, formData);
  }

  getCongeById(idConge: number) {
    const api = 'http://localhost:8081/Tessi/demande';
    return this.http.get<Conge>(`${api}/getConge?idConge=${idConge}`);
  }

  reponseConge(idConge: number, etat: String, motifRefus?: MotifRefus): Observable<any> {
    const formData = new FormData();
    formData.append('idConge', JSON.stringify(idConge));
    formData.append('etat', JSON.stringify(etat));
    return this.http.put<any>(`${this.apiUrl}/reponseconge?idConge=${idConge}&etat=${etat}`, motifRefus);
  }

  getAutorisation(autorisation : Autorisation){
    return this.http.put<any>(`${this.apiUrl}/getAutorisation`, autorisation);

  }


}
