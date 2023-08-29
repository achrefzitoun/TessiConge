import { TestBed } from '@angular/core/testing';

import { MotifsService } from './motifs.service';

describe('MotifsService', () => {
  let service: MotifsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MotifsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
