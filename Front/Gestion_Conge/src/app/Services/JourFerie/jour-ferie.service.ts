import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JourFerie } from 'src/app/Model/JourFerie';
import { Observable } from 'rxjs';
import { Politique } from 'src/app/Model/Politique';

@Injectable({
  providedIn: 'root'
})
export class JourFerieService {
  private apiUrl = 'http://localhost:8081/Tessi/conge';

  constructor(private http: HttpClient) { }

  getJourFerieByPolitique(idPolitique: number): Observable<JourFerie[]> {
    return this.http.get<JourFerie[]>(`${this.apiUrl}/getJourFerieByPolitique?idPolitique=${idPolitique}`);
  }

  retrievePolitique(idPolitique: number) {
    return this.http.get<Politique>(`${this.apiUrl}/retrievePolitique?idPolitique=${idPolitique}`);
  }

  retrieveAllPoitiquesbyJourFerie(idJourFerie: number): Observable<Politique[]> {
    return this.http.get<Politique[]>(`${this.apiUrl}/retrieveAllPoitiquesbyJourFerie?idJourFerie=${idJourFerie}`);
  }

  getJourFerieByDate(date: String) {
    return this.http.get<JourFerie>(`${this.apiUrl}/getJourFerieByDate?date=${date}`);
  }

  updateJourFerie(jourFerie: JourFerie) {
    return this.http.put<JourFerie>(`${this.apiUrl}/updateJourFerie`, jourFerie);
  }

  assignJourFeriePolitiques(idPolitique: number, idJourFerie: number) {
    return this.http.put(`${this.apiUrl}/assignJourFeriePolitiques?idPolitique=${idPolitique}&idJourFerie=${idJourFerie}`, null)
  }

  addJourFerie(jourFerie: JourFerie) {
    return this.http.post<JourFerie>(`${this.apiUrl}/addJourFerie`, jourFerie);
  }

  retrieveAllPolitique(): Observable<Politique[]> {
    return this.http.get<Politique[]>(`${this.apiUrl}/retrieveAllPolitique`);
  }

}
