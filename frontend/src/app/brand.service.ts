import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Brand } from './models/brand';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BrandService {
  constructor(private http: HttpClient) { }

  fetchBrands(): Observable<Brand[]> {
    return this.http.get<Brand[]>("http://localhost:8081/api/brands/");
  }
}
