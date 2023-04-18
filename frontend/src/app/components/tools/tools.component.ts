import { Component, OnInit } from '@angular/core';
import { ToolService, ToolSort } from '../../services/tool.service';
import { Pageable } from 'src/app/interfaces/pageable';
import { Tool } from 'src/app/models/tool';

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

  public currentPage: number = 0;
  public currentSort: ToolSort = ToolSort.NONE;

  public searchName: string = "";

  constructor(private toolService: ToolService) {
  }

  ngOnInit(): void {
    this.fetchContent()
  }

  searchByName(): void {
    if (this.searchName.length == 0)
      return this.fetchContent()

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

  fetchContent(): void {
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

  sortBy(sort: ToolSort) {
    this.currentSort = sort;
    this.fetchContent()
  }

  goNextPage() {
    if (!this.pagination.last) {
      this.currentPage += 1
      this.fetchContent()
    }
  }

  goPreviousPage() {
    if (!this.pagination.first) {
      this.currentPage -= 1
      this.fetchContent()
    }
  }
}
