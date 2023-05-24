import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { ToolService } from './tool.service';

describe('ToolService', () => {
  let service: ToolService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });

    service = TestBed.inject(ToolService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('Search all tools should make a GET request', () => {
    spyOn(service.http, 'get');

    service.searchAllTools()
    expect(service.http.get).toHaveBeenCalled()
  })

  it('Search by name should make a GET request', () => {
    spyOn(service.http, 'get');

    service.searchByName("Lorem");
    expect(service.http.get).toHaveBeenCalled()
  })

  it('Search by brand should make a GET request', () => {
    spyOn(service.http, 'get');

    service.searchByBrand('get')
    expect(service.http.get).toHaveBeenCalled()
  })
});
