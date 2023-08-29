import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MotifRefus } from 'src/app/Model/MotifRefus';

@Injectable({
  providedIn: 'root'
})
export class MotifRefusService {
  private apiUrl = 'http://localhost:8081/Tessi/conge';

  constructor(private http: HttpClient) { }

  getAllMotifs(): Observable<MotifRefus[]> {
    return this.http.get<MotifRefus[]>(`${this.apiUrl}/retrieveAllMotifRefus`);
  }
}
