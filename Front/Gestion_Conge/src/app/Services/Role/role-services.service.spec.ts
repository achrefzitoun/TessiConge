import { TestBed } from '@angular/core/testing';

import { RoleServicesService } from './role-services.service';

describe('RoleServicesService', () => {
  let service: RoleServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoleServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
