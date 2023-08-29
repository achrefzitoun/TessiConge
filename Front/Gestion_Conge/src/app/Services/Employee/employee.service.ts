import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Employee } from 'src/app/Model/Employee';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private apiUrl = 'http://localhost:8081/Tessi/conge';

  constructor(private http: HttpClient) { }

  addEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(`${this.apiUrl}/NewEmployee`, employee);
  }

  updateEmployee(employee: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.apiUrl}/UpdateEmployee`, employee);
  }

  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/DeleteEmployee/${id}`);
  }

  retrieveEmployee(id: number): Observable<Employee> {
    return this.http.get<Employee>(`${this.apiUrl}/ViewEmployee/${id}`);
  }

  retrieveAllEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.apiUrl}/ViewEmployees`);
  }
 
  getEmployees() {
    return this.http.get<any>(`${this.apiUrl}/ViewEmployees`)
        .toPromise()
        .then(res => res.data as Employee[])
        .then(data => data);
}


   

}
