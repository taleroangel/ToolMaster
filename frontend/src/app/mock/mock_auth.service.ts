import { Injectable } from "@angular/core";
import { AuthService } from "../services/auth.service";

@Injectable()
export class MockAuthService extends AuthService {

  private _authenticated: boolean = false;
  private _username: string = "";
  private _token: string = "";

  public shouldAccept = true;

  override login(user: string, password: string, onSuccessCallback: Function, onFailureCallback: Function): void {
    if (this.shouldAccept) {
      this._username = user
      this._token = 'mock-token'
      this._authenticated = true
      onSuccessCallback.call(null)
    } else {
      this.logout()
      onFailureCallback.call(null)
    }
  }

  override get authenticated(): boolean {
    return this._authenticated;
  }

  public override logout(): void {
    this._authenticated = false
  }

  override get token(): string {
    return this._token;
  }

  override get username(): string {
    return this._username;
  }
}
