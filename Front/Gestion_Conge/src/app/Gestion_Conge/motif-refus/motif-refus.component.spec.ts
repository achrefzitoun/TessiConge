import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MotifRefusComponent } from './motif-refus.component';

describe('MotifRefusComponent', () => {
  let component: MotifRefusComponent;
  let fixture: ComponentFixture<MotifRefusComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MotifRefusComponent]
    });
    fixture = TestBed.createComponent(MotifRefusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
