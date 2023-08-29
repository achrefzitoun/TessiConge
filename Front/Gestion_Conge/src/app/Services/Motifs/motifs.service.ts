import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { MotifRefus } from 'src/app/Model/MotifRefus';


@Injectable({
  providedIn: 'root'
})
export class MotifsService {

  constructor(private http: HttpClient) { }
  
  getMotif(){
    return this.http.get<MotifRefus[]>("http://localhost:8081/Tessi/motif/allMotifs");
  }

  addMotif(motif: MotifRefus){
    return this.http.post("http://localhost:8081/Tessi/motif/addMotif", motif);
  }

  getMotifById(id:number){
    return this.http.get<MotifRefus>(`http://localhost:8081/Tessi/motif/getMotif?id=${id}`);
  }

  updateMotif(motif:MotifRefus){
    return this.http.put("http://localhost:8081/Tessi/motif/updateMotif",motif);
  }

  deleteMotif(id:number){
    return this.http.delete(`http://localhost:8081/Tessi/motif/deleteMotif?id=${id}`);
  }



  
  
}
