import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { ToolsComponent } from './tools.component';
import { AuthService } from 'src/app/services/auth.service';
import { MockAuthService } from 'src/app/mock/mock_auth.service';
import { ToolService } from 'src/app/services/tool.service';
import { MockToolService } from 'src/app/mock/mock_tool.service';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from 'src/app/modules/app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

describe('ToolsComponent', () => {
  let component: ToolsComponent;
  let fixture: ComponentFixture<ToolsComponent>;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [ToolsComponent],
      imports: [BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        HttpClientModule,
        FormsModule]
    })

    TestBed.overrideComponent(ToolsComponent, {
      set: {
        providers: [
          { provide: AuthService, useClass: MockAuthService },
          { provide: ToolService, useClass: MockToolService, },
        ]
      }
    });

    await TestBed.compileComponents();

    fixture = TestBed.createComponent(ToolsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should not be authenticated', () => {
    expect(component.authenticated).toBe(false);
  });

  it('should fetch mock tools', async () => {
    await component.fetchTools()
    expect(component.tools).toBe(MockToolService.mockTools);
  })

  it('search by name should return false', () => {
    expect(component.searchByName()).toBe(false)
  })

  it('search by name should make an API request', () => {
    component.searchName = 'mock-name'
    expect(component.searchByName()).toBe(true)
  })

  it('search by brand should return false', () => {
    expect(component.searchByBrand('')).toBe(false)
  })

  it('search by brand should make an API request', () => {
    expect(component.searchByBrand('mock-brand')).toBe(true)
  })

  it('go to next page should enter if statement', () => {
    component.currentPage = 0
    component.pagination.last = false
    component.goNextPage()
    expect(component.currentPage).toBe(1)
  })

  it('go to next page should not enter if statement', () => {
    component.currentPage = 0
    component.pagination.last = true
    component.goNextPage()
    expect(component.currentPage).toBe(0)
  })

  it('go to previous page should enter if statement', () => {
    component.currentPage = 1
    component.pagination.first = false
    component.goPreviousPage()
    expect(component.currentPage).toBe(0)
  })

  it('go to previous page should not enter if statement', () => {
    component.currentPage = 1
    component.pagination.first = true
    component.goPreviousPage()
    expect(component.currentPage).toBe(1)
  })
});
