import { TestBed } from '@angular/core/testing';

import { MotifRefusService } from './motif-refus.service';

describe('MotifRefusService', () => {
  let service: MotifRefusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MotifRefusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
