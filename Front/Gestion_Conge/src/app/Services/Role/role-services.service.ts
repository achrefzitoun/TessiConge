import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from 'src/app/Model/Role';

@Injectable({
  providedIn: 'root'
})
export class RoleServicesService {

  private apiUrl = 'http://localhost:8081/Tessi/conge';

  constructor(private http:HttpClient) { }

  addRole(role: Role): Observable<Role> {
    return this.http.post<Role>(`${this.apiUrl}/NewRole`, role);
  }

  updateRole(role: Role): Observable<Role> {
    return this.http.put<Role>(`${this.apiUrl}/UpdateRole`, role);
  }

  deleteRole(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/DeleteRole/${id}`);
  }

  retrieveRole(id: number): Observable<Role> {
    return this.http.get<Role>(`${this.apiUrl}/ViewRole/${id}`);
  }

  retrieveAllRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`${this.apiUrl}/ViewRoles`);
  }
}
