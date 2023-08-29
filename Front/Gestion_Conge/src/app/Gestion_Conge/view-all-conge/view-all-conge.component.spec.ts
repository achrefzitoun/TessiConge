import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllCongeComponent } from './view-all-conge.component';

describe('ViewAllCongeComponent', () => {
  let component: ViewAllCongeComponent;
  let fixture: ComponentFixture<ViewAllCongeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewAllCongeComponent]
    });
    fixture = TestBed.createComponent(ViewAllCongeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
