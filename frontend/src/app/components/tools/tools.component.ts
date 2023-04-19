import { Component, OnInit } from '@angular/core';
import { ToolService, ToolSort } from '../../services/tool.service';
import { Pageable } from 'src/app/interfaces/pageable';
import { Tool } from 'src/app/models/tool';
import { AuthService } from 'src/app/services/auth.service';
import { Brand } from 'src/app/models/brand';
import { BrandService } from 'src/app/brand.service';

@Component({
  selector: 'app-tools',
  templateUrl: './tools.component.html',
  styleUrls: ['./tools.component.scss']
})
export class ToolsComponent implements OnInit {

  public TS = ToolSort;
  public isReady = false;

  public pagination!: Pageable<Tool>;
  public tools!: Tool[];
  public brands!: Brand[];

  public currentPage: number = 0;
  public currentSort: ToolSort = ToolSort.NONE;

  public searchName: string = "";
  public brandFilter: string = "";

  constructor(
    private toolService: ToolService,
    public authService: AuthService,
    private brandService: BrandService) {
  }

  ngOnInit(): void {
    this.fetchTools()
    this.fetchBrands()
  }

  fetchTools(): void {
    this.toolService.searchAllTools(this.currentSort, this.currentPage).subscribe({
      next: data => {
        this.pagination = data;
        this.tools = data.content;
        this.isReady = true;
      },
      error: error => {
        console.error(error);
        alert("Error!, No se ha podido conectar con el servidor")
      }
    })
  }

  fetchBrands(): void {
    this.brandService.fetchBrands().subscribe({
      next: data => {
        this.brands = data;
      },
      error: error => {
        console.error(error);
        alert("Error!, No se ha podido conectar con el servidor")
      }
    })
  }

  searchByName(): void {
    if (this.searchName.length == 0)
      return this.fetchTools()

    this.toolService.searchByName(this.searchName, this.currentSort, this.currentPage).subscribe({
      next: data => {
        this.pagination = data;
        this.tools = data.content;
        this.isReady = true;
      },
      error: error => {
        console.error(error);
        alert("Error!, No se ha podido conectar con el servidor")
      }
    })
  }

  searchByBrand(brand: string): void {
    this.brandFilter = brand

    if (this.brandFilter.length == 0)
      return this.fetchTools()

    this.toolService.searchByBrand(this.brandFilter, this.currentSort, this.currentPage).subscribe({
      next: data => {
        this.pagination = data;
        this.tools = data.content;
        this.isReady = true;
      },
      error: error => {
        console.error(error);
        alert("Error!, No se ha podido conectar con el servidor")
      }
    })
  }

  sortBy(sort: ToolSort) {
    this.currentSort = sort;
    this.fetchTools()
  }

  goNextPage() {
    if (!this.pagination.last) {
      this.currentPage += 1
      this.fetchTools()
    }
  }

  goPreviousPage() {
    if (!this.pagination.first) {
      this.currentPage -= 1
      this.fetchTools()
    }
  }
}
