import { TestBed } from '@angular/core/testing';

import { TypeCongeService } from './type-conge.service';

describe('TypeCongeService', () => {
  let service: TypeCongeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeCongeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
