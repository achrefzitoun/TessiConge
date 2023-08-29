import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Autorisation } from 'src/app/Model/Autorisation';

@Injectable({
  providedIn: 'root'
})
export class DashbordService {

  constructor(private http: HttpClient) { }

  nbCongeAujourdhui() {
    return this.http.get<number>("http://localhost:8081/Tessi/dashbord/nbCongeAujourdhui");
  }

  nbCongeMois() {
    return this.http.get<number>("http://localhost:8081/Tessi/dashbord/nbCongeMois");
  }

  nbEnAttente(){
    return this.http.get<number>("http://localhost:8081/Tessi/dashbord/nbEnAttente");
  }

  nbAutorisation(){
    return this.http.get<number>("http://localhost:8081/Tessi/dashbord/nbAutorisation");
  }
  
  countEmp(){
    return this.http.get<number>("http://localhost:8081/Tessi/dashbord/countEmp");
  }

  getListAutorisation(): Observable<Autorisation[]> {
    return this.http.get<Autorisation[]>(`http://localhost:8081/Tessi/dashbord/getListAutorisation`);
  }
  
  getNbCongeMois(mois: number){
    return this.http.get<number>(`http://localhost:8081/Tessi/dashbord/getNbCongeMois?mois=${mois}`);
  }
}
