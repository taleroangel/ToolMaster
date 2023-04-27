import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { NavBarComponent } from './nav-bar.component';
import { AppRoutingModule } from 'src/app/modules/app-routing.module';
import { MockAuthService } from 'src/app/mock/mock_auth.service';
import { AuthService } from 'src/app/services/auth.service';

describe('NavBarComponent', () => {
  let component: NavBarComponent;
  let fixture: ComponentFixture<NavBarComponent>;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [NavBarComponent],
      imports: [HttpClientModule, AppRoutingModule]
    });

    TestBed.overrideComponent(NavBarComponent, {
      set: {
        providers: [{
          provide: AuthService, useClass: MockAuthService
        }]
      }
    });

    await TestBed.compileComponents();

    fixture = TestBed.createComponent(NavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not be authenticated', () => {
    expect(component.authenticated).toBe(false);
  });
});
