import { Component } from '@angular/core';
import { LayoutService } from 'src/app/layout/service/app.layout.service';
import { Employee } from '../Model/Employee';

@Component({
  selector: 'app-login-tessi',
  templateUrl: './login-tessi.component.html',
  styles: [`
        :host ::ng-deep .pi-eye,
        :host ::ng-deep .pi-eye-slash {
            transform:scale(1.6);
            margin-right: 1rem;
            color: var(--primary-color) !important;
        }
    `]
})
export class LoginTessiComponent {
  valCheck: string[] = ['remember'];

  employee: Employee = new Employee();
  constructor(public layoutService: LayoutService) { }

}
