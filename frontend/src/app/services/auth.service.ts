import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Token } from '../interfaces/token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  constructor(
    private http: HttpClient,
    private cookies: CookieService
  ) { }

  public login(user: string, password: string, onSuccessCallback: Function, onFailureCallback: Function): void {
    this.http.post<Token>("http://localhost:8080/api/auth/login", {
      username: user,
      password: password
    }).subscribe(
      {
        next: data => {
          this.token = data.token;
          this.username = data.username;
          onSuccessCallback()
        },
        error: error => {
          console.error(error);
          onFailureCallback()
        }
      }
    )
  }

  public logout() {
    this.cookies.delete('token')
    this.cookies.delete('username')
  }

  private set username(username: string) {
    this.cookies.set('username', username)
  }

  public get username(): string {
    return this.cookies.get('username')
  }

  private set token(token: string) {
    this.cookies.set('token', token)
  }

  public get token(): string {
    return this.cookies.get('token')
  }

  public get authenticated(): boolean {
    return this.token.length != 0
  }
}
