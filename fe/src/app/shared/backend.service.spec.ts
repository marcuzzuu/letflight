import { TestBed } from '@angular/core/testing';

import { BackendService } from './backend.service';

describe('BackendServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BackendService = TestBed.get(BackendService);
    expect(service).toBeTruthy();
  });
});
