import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMyCongeComponent } from './view-my-conge.component';

describe('ViewMyCongeComponent', () => {
  let component: ViewMyCongeComponent;
  let fixture: ComponentFixture<ViewMyCongeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewMyCongeComponent]
    });
    fixture = TestBed.createComponent(ViewMyCongeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
