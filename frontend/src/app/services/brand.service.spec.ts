import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { BrandService } from './brand.service';
import { Brand } from '../models/brand';

describe('BrandService', () => {
  let service: BrandService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule]
    });
    service = TestBed.inject(BrandService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('Should get all brands', async () => {
    await expectAsync(new Promise<void>((resolve, reject) => {
      service.fetchBrands().subscribe({
        next: (brands: Brand[]) => { resolve() },
        error: (error: any) => { reject() }
      })
    })).toBeResolved()
  })
});
