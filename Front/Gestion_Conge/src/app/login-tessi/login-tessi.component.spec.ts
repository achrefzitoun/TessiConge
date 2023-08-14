import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginTessiComponent } from './login-tessi.component';

describe('LoginTessiComponent', () => {
  let component: LoginTessiComponent;
  let fixture: ComponentFixture<LoginTessiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginTessiComponent]
    });
    fixture = TestBed.createComponent(LoginTessiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
