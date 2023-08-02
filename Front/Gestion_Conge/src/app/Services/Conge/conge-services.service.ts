import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Conge } from 'src/app/Model/Conge';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/Model/Employee';

@Injectable({
  providedIn: 'root'
})
export class CongeServicesService {

  private apiUrl = 'http://localhost:8081/Tessi/conge';

  constructor(private http:HttpClient) {}
  
  getAllEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.apiUrl}/GetAllEmplyees`);
  }

  affectationConge(conge: Conge, idDemandeur: number, idTypeConge: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/affectationConge/${idDemandeur}/${idTypeConge}`, conge);
  }

}
