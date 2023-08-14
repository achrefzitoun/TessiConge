import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudEmployeeComponent } from './crud-employee.component';

describe('CrudEmployeeComponent', () => {
  let component: CrudEmployeeComponent;
  let fixture: ComponentFixture<CrudEmployeeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CrudEmployeeComponent]
    });
    fixture = TestBed.createComponent(CrudEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
