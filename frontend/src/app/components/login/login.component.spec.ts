import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { LoginComponent } from './login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { MockAuthService } from 'src/app/mock/mock_auth.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [HttpClientModule, FormsModule, ReactiveFormsModule],
      providers: [{
        provide: AuthService, useClass: MockAuthService
      }]
    });

    TestBed.overrideComponent(LoginComponent, {
      set: {
        providers: [{
          provide: AuthService, useClass: MockAuthService
        }]
      }
    });

    await TestBed.compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Should create', () => {
    expect(component).toBeTruthy();
  });

  it('Should not be authenticated', () => {
    expect(component.authenticated).toBe(false);
  })

  it('Should prompt an error message', () => {
    spyOn(window, "alert");
    component.onSubmit()
    expect(window.alert).toHaveBeenCalledWith("Debe llenar los campos!");
  })

  it('Should prompt a session logout message', () => {
    spyOn(window, "alert");
    component.logOut(false)
    expect(window.alert).toHaveBeenCalledWith("Sesión cerrada correctamente");
  })

  it('Should show alert insted of navigate to URL', async () => {
    expect(component.checkoutForm.valid).toBeFalse();
  })

  it('Should navigate to URL instead of showing alert', async () => {
    spyOn(component.router, "navigateByUrl");

    component.checkoutForm.setValue({ user: "username", password: "password" });
    expect(component.checkoutForm.valid).toBeTrue();

    await expectAsync(component.onSubmit()).toBeResolved()
  })

  it('Should show alert instead of navigating', async () => {
    (component.authService as MockAuthService).shouldAccept = false;
    spyOn(component.router, "navigateByUrl");
    spyOn(window, "alert");

    component.checkoutForm.setValue({ user: "username", password: "password" });
    expect(component.checkoutForm.valid).toBeTrue();

    await expectAsync(component.onSubmit()).toBeRejected()
    expect(window.alert).toHaveBeenCalled()
  })

  it('Log out default parameter should call logout', () => {
    spyOn(component.authService, 'logout')
    component.logOut();
    expect(component.authService.logout).toHaveBeenCalled()
  })

  it('Log out with true parameter should call navigateByUrl', () => {
    spyOn(component.router, "navigateByUrl");
    component.logOut(true);
    expect(component.router.navigateByUrl).toHaveBeenCalled()
  })

  it('Log out with false parameter should not call navigateByUrl', () => {
    spyOn(component.router, "navigateByUrl");
    component.logOut(false);
    expect(component.router.navigateByUrl).not.toHaveBeenCalled()
  })
});
