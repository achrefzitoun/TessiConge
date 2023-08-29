import { TestBed } from '@angular/core/testing';

import { PolitiqueService } from './politique.service';

describe('PolitiqueService', () => {
  let service: PolitiqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PolitiqueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
