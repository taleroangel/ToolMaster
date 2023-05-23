import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';

import { UsersComponent } from './users.component';
import { AppComponent } from 'src/app/app.component';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from 'src/app/modules/app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MockAuthService } from 'src/app/mock/mock_auth.service';
import { AuthService } from 'src/app/services/auth.service';
import { MockUserService } from 'src/app/mock/mock_user_service';
import { UserService, UserSort } from 'src/app/services/user.service';

describe('UsersComponentHasToBeAuthenticated', () => {
  let component: UsersComponent;
  let fixture: ComponentFixture<UsersComponent>;
  let authService: MockAuthService;

  beforeEach(async () => {
    // Configure testing module
    await TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        UsersComponent,
      ],
      imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
      ],
      providers: [
        HttpClientModule,
        FormsModule,
        { provide: AuthService, useClass: MockAuthService }
      ],
    }).compileComponents();

    // Ensure no authentication
    authService = TestBed.inject(AuthService) as MockAuthService;
    authService.logout()
  });

  it('Ensure mock authentication', async () => {
    expect(authService.authenticated).toBe(false)

    let mockAuth = new Promise<void>((resolve, reject) => {
      authService.login("", "", () => {
        resolve()
      }, () => {
        reject("Is not mock!")
      })
    })

    await expectAsync(mockAuth).toBeResolved()

    // Create the component
    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance;

    // Expect it to be authenticated
    expect(component.authService.authenticated).toBe(true)
  })

  it('should create', () => {
    // Create the component
    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance;

    // Expect it to be created
    expect(component).toBeTruthy();
  });

  it('should not be authenticated', () => {
    // Create the component
    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance;

    // Spy on fetchUsers
    spyOn(component, 'fetchUsers')

    expect(component.authenticated).toBeFalse()
    expect(component.fetchUsers).not.toHaveBeenCalled()
  })

  it('should be authenticated', async () => {

    let promise = new Promise<void>((resolve, reject) => {
      authService.login("john_doe", "john_ata", () => {
        resolve()
      }, () => {
        reject("Authentication was nos successful")
      })
    })

    await expectAsync(promise).toBeResolved()

    // Create the component
    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance;

    // Spy on fetchUsers
    let spy = spyOn(component, 'fetchUsers')

    // Await stabilization
    await fixture.whenStable()
    fixture.detectChanges()

    // Expect it to be authenticated on both
    expect(authService.authenticated).toBeTrue()
    expect(component.authenticated).toBeTrue()

    // Fetch users must have been called one
    expect(spy).toHaveBeenCalled()
  })
});

describe('UsersComponentAuthenticated', () => {
  let component: UsersComponent;
  let fixture: ComponentFixture<UsersComponent>;
  let authService: MockAuthService;
  let userService: MockUserService;

  beforeEach(async () => {
    // Configure testing module
    await TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        UsersComponent,
      ],

      imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        HttpClientModule,
        FormsModule
      ],

      providers: [
        HttpClientModule,
        FormsModule,
        { provide: AuthService, useClass: MockAuthService },
        { provide: UserService, useClass: MockUserService },
      ],
    }).compileComponents()

    // Ensure no authentication
    authService = TestBed.inject(AuthService) as MockAuthService
    userService = TestBed.inject(UserService) as MockUserService;

    await new Promise<void>((resolve, reject) => {
      authService.login("", "", () => {
        resolve()
      }, () => {
        reject()
      })
    })

    // Create the component
    fixture = TestBed.createComponent(UsersComponent)
    component = fixture.componentInstance
    fixture.detectChanges()

    await fixture.whenStable()
  });

  it("Search by name should search all users", () => {
    // Empty search name
    component.searchName = ""

    // Spy on fetchUsers
    spyOn(component, 'fetchUsers')
    spyOn(userService, 'searchByName')

    // Call search by name
    component.searchByName()

    // Expect it to have been called
    expect(component.fetchUsers).toHaveBeenCalled()
    expect(userService.searchByName).not.toHaveBeenCalled()
  });

  it("Search by name should search by name only", () => {
    // Empty search name
    component.searchName = "Example"

    // Spy on fetchUsers
    spyOn(component, 'fetchUsers')

    // Call search by name
    component.searchByName()

    // Expect it to have been called
    expect(component.fetchUsers).not.toHaveBeenCalled()
  });

  it("Go to the next route should call fetchUsers", async () => {
    spyOn(component, 'fetchUsers')
    component.pagination.last = false

    component.goNextPage()
    expect(component.fetchUsers).toHaveBeenCalled()
  })

  it("Go to the next route should not call fetchUsers", async () => {
    spyOn(component, 'fetchUsers')
    component.pagination.last = true

    component.goNextPage()
    expect(component.fetchUsers).not.toHaveBeenCalled()
  })

  it("Go to the previous route should call fetchUsers", async () => {
    spyOn(component, 'fetchUsers')
    component.pagination.first = false

    component.goPreviousPage()
    expect(component.fetchUsers).toHaveBeenCalled()
  })

  it("Go to the previous route should not call fetchUsers", async () => {
    spyOn(component, 'fetchUsers')
    component.pagination.first = true

    component.goPreviousPage()
    expect(component.fetchUsers).not.toHaveBeenCalled()
  })

  it("SortBy should call fetchUsers", () => {
    spyOn(component, 'fetchUsers')
    component.sortBy(UserSort.ID)
    expect(component.fetchUsers).toHaveBeenCalled()
  })

  it("Delete user should not call fetchUsers on error", () => {
    spyOn(component, 'fetchUsers')
    component.deleteUser(-1)
    expect(component.fetchUsers).not.toHaveBeenCalled()
  })

  it("Delete user should call fetchUsers on success", () => {
    spyOn(component, 'fetchUsers')
    component.deleteUser(1)
    expect(component.fetchUsers).toHaveBeenCalled()
  })
});
