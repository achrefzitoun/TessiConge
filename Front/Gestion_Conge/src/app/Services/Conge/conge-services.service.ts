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

  constructor(private http: HttpClient, private datePipe: DatePipe) { }

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

    
  getAllConges() {
    return this.http.get<Conge[]>("http://localhost:8081/Tessi/demande/allConge");
  }

  getAllCongesEnAttente() {
    return this.http.get<Conge[]>("http://localhost:8081/Tessi/demande/CongeEnAttente");
  }

  
  getCongesByEmploye() {
    return this.http.get<Conge[]>("http://localhost:8081/Tessi/demande/allCongeEmployee");
  }

  getOneConge(id: number) {
    return this.http.get<Conge>(`http://localhost:8081/Tessi/demande/getConge?id=${id}`);
  }

  addConge(conge: Conge, file: File, idtype: number, startDate: string, endDate: string): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('idtype', idtype.toString());
    formData.append('startDate', startDate.toString());
    formData.append('endDate', endDate.toString());
    formData.append('conge', JSON.stringify(conge));
    return this.http.put<any>('http://localhost:8081/Tessi/demande/addAndAssignType', formData);
  }

  updateConge(conge: Conge, file: File, idtype: number, startDate: string, endDate: string) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('idtype', idtype.toString());
    formData.append('startDate', startDate.toString());
    formData.append('endDate', endDate.toString());
    formData.append('conge', JSON.stringify(conge));
    return this.http.put('http://localhost:8081/Tessi/demande/updateConge', formData, { responseType: 'text' });
  }


  annulConge(id: number) {
    return this.http.put(`http://localhost:8081/Tessi/demande/annulCong?id=${id}`, {});
  }


  getAllType(): Observable<TypeConge[]> {
    return this.http.get<TypeConge[]>("http://localhost:8081/Tessi/demande/allTypeConge");
  }


  getDurationN(startDate: String, endDate: String) {

    const params = new HttpParams()
      .set('startDate', startDate.toString())
      .set('endDate', endDate.toString());

    return this.http.get<number>("http://localhost:8081/Tessi/demande/durationN", { params });
  }

  getDurationS(startDate: String, idtype: number) {

    const params = new HttpParams()
      .set('startDate', startDate.toString())
      .set('idtype', idtype.toString());

    return this.http.get<number>("http://localhost:8081/Tessi/demande/durationS", { params });
  }

  getTypeCongebyNature(natureType: NatureType): Observable<TypeConge[]> {
    return this.http.get<TypeConge[]>("http://localhost:8081/Tessi/demande/getTypeCongebyNature/" + natureType);
  }

  getEmployeesDelegDev() {
    return this.http.get<Employee[]>("http://localhost:8081/Tessi/demande/getemployeedelegDev");
  }

  getEmployeesDelegContr() {
    return this.http.get<Employee[]>("http://localhost:8081/Tessi/demande/getemployeedelegContr");
  }

  delegationRole(idEmpl: number, startDate: string, endDate: string) {
    const formData = new FormData();
    formData.append('idEmpl', idEmpl.toString());
    formData.append('startDate', startDate.toString());
    formData.append('endDate', endDate.toString());
    return this.http.put<any>('http://localhost:8081/Tessi/demande/delegation', formData);

  }

  exportCongeExcel(): Observable<HttpResponse<Blob>> {
    const headers = new HttpHeaders({ 'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    return this.http.get<Blob>('http://localhost:8081/Tessi/demande/exportExcel', { headers: headers, observe: 'response', responseType: 'blob' as 'json' });
  }

  getGeneratedPdf() {

    return this.http.get('http://localhost:8081/Tessi/demande/generatepdf', { responseType: 'blob' });
  }

  getAllCongeAccepr() {
    return this.http.get<Conge[]>("http://localhost:8081/Tessi/demande/getAllCongesAccep");
  }

  getEmployesBetweenDates(startDate: string, endDate: string): Observable<Employee[]> {
    const url = `http://localhost:8081/Tessi/demande/getEmployesBetweenDates?startDate=${startDate}&endDate=${endDate}`;
    return this.http.get<Employee[]>(url);
  }

  getEmplyesContainDate(startDate: string): Observable<Employee[]> {
    const url = `http://localhost:8081/Tessi/demande/getEmplyesContainDate?startDate=${startDate}`;
    return this.http.get<Employee[]>(url);
  }

  getCongesSuperviseurs(): Observable<Conge[]> {
    return this.http.get<Conge[]>("http://localhost:8081/Tessi/demande/getListCongeDesSuperviseurs");
  }

  
  getDelegueEmployeeName(idConge: number): Observable<string> {
    const url = `http://localhost:8081/Tessi/demande/getDelegueEmployeeName?idConge=${idConge}`;
    return this.http.get(url, { responseType: 'text' }); // Définit le type de réponse comme 'text'
  }



}
