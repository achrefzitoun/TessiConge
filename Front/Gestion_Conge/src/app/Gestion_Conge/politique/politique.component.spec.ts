import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PolitiqueComponent } from './politique.component';

describe('PolitiqueComponent', () => {
  let component: PolitiqueComponent;
  let fixture: ComponentFixture<PolitiqueComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PolitiqueComponent]
    });
    fixture = TestBed.createComponent(PolitiqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
