import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TypeConge } from 'src/app/Model/TypeConge';
import { Politique } from 'src/app/Model/Politique';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TypeCongeService {

  private apiUrl = 'http://localhost:8081/Tessi/conge';

  constructor(private http: HttpClient) { }

  addTypeConge(typeConge: TypeConge, politiques: Politique[]): Observable<TypeConge> {
    const requestBody = {
      typeConge: typeConge,
      politiques: politiques
    };
    return this.http.post<TypeConge>(`${this.apiUrl}/addTypeConge`, requestBody);
  }

  deleteTypeConge(idType: number) {
    return this.http.put(`${this.apiUrl}/deleteTypeConge/${idType}`, null);
  }

  retrieveAllPolitiquesByType(idType : number): Observable<Politique[]>{
    return this.http.get<Politique[]>(`${this.apiUrl}/retrieveAllPolitiquesByType/${idType}`);
  }

  editTypeConge(typeConge: TypeConge, politiques: Politique[]): Observable<TypeConge> {
    const requestBody = {
      typeConge: typeConge,
      politiques: politiques
    };
    return this.http.put<TypeConge>(`${this.apiUrl}/editTypeConge`, requestBody);
  }


}
