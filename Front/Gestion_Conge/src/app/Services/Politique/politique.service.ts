import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Politique } from 'src/app/Model/Politique';
import { Employee } from 'src/app/Model/Employee';


@Injectable({
  providedIn: 'root'
})
export class PolitiqueService {

  constructor(private http: HttpClient) { }


  getPolitique(){
    return this.http.get<Politique[]>("http://localhost:8081/Tessi/politique/allPolitiques");
  }

  addPolitique(politique: Politique){
    return this.http.post("http://localhost:8081/Tessi/politique/addPolitique", politique);
  }

  getPolitiqueById(id:number){
    return this.http.get<Politique>(`http://localhost:8081/Tessi/politique/getPolitique?id=${id}`);
  }

  updatePolitique(politique:Politique){
    return this.http.put("http://localhost:8081/Tessi/politique/updatePolitique",politique);
  }

  deletePolitique(id:number){
    return this.http.delete(`http://localhost:8081/Tessi/politique/deletePolitique?id=${id}`);
  }

  getEmployeeByPolitique(id:number){
    return this.http.get<Employee[]>(`http://localhost:8081/Tessi/politique/employeByPolitique?id=${id}`);
  }

}
