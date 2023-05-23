import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { AuthService } from './auth.service';
import { CookieService } from 'ngx-cookie-service';

describe('AuthService', () => {
  let service: AuthService;
  let cookieService: CookieService;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });

    await TestBed.compileComponents();

    service = TestBed.inject(AuthService);
    cookieService = TestBed.inject(CookieService);

    service.logout();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('cookie should not exist', async () => {
    // Cookie should not exist
    expect(cookieService.check("username")).toBe(false)
    expect(cookieService.check("token")).toBe(false)
  })

  it('should create cookie', async () => {
    // Cookie should not exist
    expect(cookieService.check("username")).toBe(false)
    expect(cookieService.check("token")).toBe(false)

    // Login
    let promise = new Promise<void>((resolve, reject) => {
      service.login("john_doe", "john_doe", () => {
        resolve()
      }, () => {
        reject("Authentication failed")
      })
    })

    await promise

    // Cookie should exist
    expect(cookieService.check("username")).toBe(true)
    expect(cookieService.check("token")).toBe(true)

    // logout again to prevent other tests from failing
    service.logout();

    // Cookie should exist
    expect(cookieService.check("username")).toBe(false)
    expect(cookieService.check("token")).toBe(false)
  })

  it('should fail authentication', async () => {
    // Cookie should not exist
    expect(cookieService.check("username")).toBe(false)
    expect(cookieService.check("token")).toBe(false)

    // Login
    let promise = new Promise<void>((resolve, reject) => {
      service.login("", "", () => {
        resolve()
      }, () => {
        reject("Authentication failed")
      })
    })

    // Expect reject
    await expectAsync(promise).toBeRejected()

    // Cookie should exist
    expect(cookieService.check("username")).toBe(false)
    expect(cookieService.check("token")).toBe(false)
  })

  it('getters should work when authenticated', async () => {
    expect(service.username).toBe('')
    expect(service.token).toBe('')
    expect(service.authenticated).toBeFalse()

    // Login
    let promise = new Promise<void>((resolve, reject) => {
      service.login("john_doe", "john_doe", () => {
        resolve()
      }, () => {
        reject("Authentication failed")
      })
    })

    await expectAsync(promise).toBeResolved()

    expect(service.username).toBe('john_doe')
    expect(service.token).toBeTruthy()
    expect(service.authenticated).toBeTrue()
  })
});
