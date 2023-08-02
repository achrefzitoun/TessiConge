import { TestBed } from '@angular/core/testing';

import { CongeServicesService } from './conge-services.service';

describe('CongeServicesService', () => {
  let service: CongeServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CongeServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
