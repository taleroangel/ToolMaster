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

  constructor(private toolService: ToolService) {
  }

  ngOnInit(): void {
    this.toolService.searchAllTools().subscribe(data => {
      this.pagination = data;
      this.tools = data.content;
      this.isReady = true;
    })
  }

  sortBy(sort: ToolSort): void {
    this.toolService.searchAllTools(sort).subscribe(data => {
      this.pagination = data;
      this.tools = data.content;
    })
  }
}
