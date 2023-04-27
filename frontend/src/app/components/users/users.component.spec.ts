import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { UsersComponent } from './users.component';
import { AppComponent } from 'src/app/app.component';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { LoginComponent } from '../login/login.component';
import { ToolsComponent } from '../tools/tools.component';
import { WelcomeComponent } from '../welcome/welcome.component';
import { FooterComponent } from '../footer/footer.component';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from 'src/app/modules/app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

describe('UsersComponent', () => {
  let component: UsersComponent;
  let fixture: ComponentFixture<UsersComponent>;

  beforeEach(async () => {
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
        FormsModule
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
