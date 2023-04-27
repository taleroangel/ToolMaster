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
      imports: [HttpClientModule, FormsModule, ReactiveFormsModule]
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

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not be authenticated', () => {
    expect(component.authenticated).toBe(false);
  })

  it('should prompt an error message', () => {
    spyOn(window, "alert");
    component.onSubmit()
    expect(window.alert).toHaveBeenCalledWith("Debe llenar los campos!");
  })

  it('should prompt a session logout message', () => {
    spyOn(window, "alert");
    component.logOut(false)
    expect(window.alert).toHaveBeenCalledWith("Sesión cerrada correctamente");
  })
});
