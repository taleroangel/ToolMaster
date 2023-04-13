import { Component, OnInit } from '@angular/core';
import { ToolService } from '../tool.service';
import { Pageable } from 'src/interfaces/pageable';
import { Tool } from 'src/models/tool';

@Component({
  selector: 'app-tools',
  templateUrl: './tools.component.html',
  styleUrls: ['./tools.component.scss']
})
export class ToolsComponent implements OnInit{

  public tools: Tool[] = [];

  constructor(private toolService: ToolService) {
  }

  ngOnInit(): void {
    this.toolService.searchAllTools().subscribe(data => {
      this.tools = data.content;
    })
  }
}
