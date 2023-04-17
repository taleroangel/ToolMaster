import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Pageable } from 'src/interfaces/pageable';
import { Tool } from 'src/models/tool';

@Injectable({
  providedIn: 'root'
})
export class ToolService {

  constructor(private http: HttpClient) { }

  searchAllTools(): Observable<Pageable<Tool>> {
    return this.http.get<Pageable<Tool>>("http://localhost:8081/api/tools/")
  }
}
