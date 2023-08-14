import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Employee } from 'src/app/Model/Employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) { }

    getEmployeesSmall() {
        return this.http.get<any>('assets/demo/data/employees-small.json')
            .toPromise()
            .then(res => res.data as Employee[])
            .then(data => data);
    }

    getEmployees() {
        return this.http.get<any>('assets/demo/data/employees.json')
            .toPromise()
            .then(res => res.data as Employee[])
            .then(data => data);
    }

    getEmployeesMixed() {
        return this.http.get<any>('assets/demo/data/employees-mixed.json')
            .toPromise()
            .then(res => res.data as Employee[])
            .then(data => data);
    }

    getEmployeesWithOrdersSmall() {
        return this.http.get<any>('assets/demo/data/employees-orders-small.json')
            .toPromise()
            .then(res => res.data as Employee[])
            .then(data => data);
    }
}
