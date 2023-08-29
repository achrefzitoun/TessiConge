import { TestBed } from '@angular/core/testing';

import { ListCongeService } from './list-conge.service';

describe('ListCongeService', () => {
  let service: ListCongeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListCongeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
