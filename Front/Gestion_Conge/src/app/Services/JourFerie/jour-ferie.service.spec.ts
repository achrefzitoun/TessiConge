import { TestBed } from '@angular/core/testing';

import { JourFerieService } from './jour-ferie.service';

describe('JourFerieService', () => {
  let service: JourFerieService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JourFerieService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
