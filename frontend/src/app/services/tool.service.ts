import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pageable } from 'src/app/interfaces/pageable';
import { Tool } from 'src/app/models/tool';

@Injectable({
  providedIn: 'root'
})
export class ToolService {

  constructor(private http: HttpClient) { }


  searchAllTools(sort: ToolSort = ToolSort.NONE, page: number = 0): Observable<Pageable<Tool>> {
    const params = new HttpParams().set('sort', sort).set('page', page);
    return this.http.get<Pageable<Tool>>("http://localhost:8081/api/tools/", { params: params })
  }
}

export enum ToolSort {
  NONE = '',
  PRICE = 'price',
  NAME = 'name',
  BRAND = 'brand.name',
  CITY = 'cities.name',
}
