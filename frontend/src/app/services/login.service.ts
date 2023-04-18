import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private http: HttpClient,
    private cookies: CookieService
  ) { }

  login(user: string, password: string): Observable<any> {
    return this.http.post("http://localhost:8080/api/auth/login", {
      username: user,
      password: password
    })
  }

  setToken(token: string) {
    this.cookies.set('token', token)
  }

  getToken() {
    return this.cookies.get('token')
  }

  hasToken(): boolean {
    return this.getToken().length != 0
  }
}
