import { AuthService } from "../services/auth.service";

export class MockAuthService extends AuthService {
    override login(user: string, password: string, onSuccessCallback: Function, onFailureCallback: Function): void {
        this.username = user
        this.token = 'mock-token'
    }
}