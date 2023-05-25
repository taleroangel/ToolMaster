import { Component, OnInit } from '@angular/core';
import { ToolService, ToolSort } from '../../services/tool.service';
import { Pageable } from 'src/app/interfaces/pageable';
import { Tool } from 'src/app/models/tool';
import { AuthService } from 'src/app/services/auth.service';
import { Brand } from 'src/app/models/brand';
import { BrandService } from 'src/app/services/brand.service';

/**
 * Componente que muestra las cards con las diferentes herramientas
 */
@Component({
  selector: 'app-tools',
  templateUrl: './tools.component.html',
  styleUrls: ['./tools.component.scss']
})
export class ToolsComponent implements OnInit {

  /**
   * Alias a ToolSort para uso dentro del html
   */
  TS = ToolSort;

  /**
   * Variable que indica si está listo el contenido de la página
   */
  isReady = false;

  /**
   * Información sobre la paginación de herramientas para mostrar la página actual
   */
  pagination!: Pageable<Tool>;

  /**
   * Listado de las herramientas a mostrar
   */
  tools!: Tool[];

  /**
   * Listado de las marcas para el filtro de marca
   */
  brands: Brand[] = [];

  /**
   * Página actual para mostrar en la paginación
   */
  currentPage: number = 0;

  /**
  * Criterio de ordenamiento actual
  */
  currentSort: ToolSort = ToolSort.NONE;

  /**
  * Variable para almacenar el nombre de la herramienta buscar,
  * está adjunta mediante un [ngModel] al input de búsqueda
  */
  searchName: string = "";

  /**
   * Variable para almacenar el filtro de marca actual,
   * si está vacío no se aplica filtro
   */
  brandFilter: string = "";

  /**
   * Constructor con los servicios inyectados
   * @param toolService Servicio de Herramientas
   * @param brandService Servicio de marcas
   * @param authService Servicio de autenticación
   */
  constructor(
    private toolService: ToolService,
    private brandService: BrandService,
    private authService: AuthService,
  ) {
  }

  /**
   * Cargar contenido una vez se inicie la página
   */
  ngOnInit(): void {
    this.fetchTools()
    this.fetchBrands()
  }

  /**
   * Verificar si se está autenticado
   * @returns True si está autenticado
   */
  get authenticated(): boolean {
    return this.authService.authenticated
  }

  /**
   * Obtener las herramientas utilizando las variables de ordenamiento y paginado
   */
  fetchTools(): Promise<boolean> {
    return new Promise<boolean>((resolve, _) => {
      this.toolService.searchAllTools(this.currentSort, this.currentPage).subscribe({
        next: data => {
          this.pagination = data;
          this.tools = data.content;
          this.isReady = true;
          resolve(true)
        },
        error: error => {
          console.error(error);
          alert("Error!, No se ha podido conectar con el servidor")
          resolve(false)
        }
      })
    });
  }

  /**
   * Obtener un listado de todas las marcas
   */
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

  /**
   * Obtener las herramientas cuyo nombre coincida o incluya el especificado
   */
  searchByName(): boolean {
    if (this.searchName.length == 0) {
      this.fetchTools()
      return false
    } else {
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

      return true
    }
  }

  /**
   * Buscar las herramientas cuya marca coincida o incluya la especificada
   * @param brand Marca por la cual filtrar=
   */
  searchByBrand(brand: string): boolean {
    this.brandFilter = brand

    if (this.brandFilter.length == 0) {
      this.fetchTools()
      return false

    } else {
      this.toolService.searchByBrand(this.brandFilter, this.currentSort, this.currentPage).subscribe({
        next: data => {
          this.pagination = data;
          this.tools = data.content;
        },
        error: error => {
          console.error(error);
          alert("Error!, No se ha podido conectar con el servidor")
        }
      })
      return true

    }
  }

  /**
   * Cambiar el criterio de ordenamiento y realizar una nueva petición
   * @param sort Criterio de ordenamiento
   */
  sortBy(sort: ToolSort): void {
    this.currentSort = sort;
    this.fetchTools()
    this.searchByBrand(this.brandFilter)
  }

  /**
   * Cambiar la paginación a la siguiente página
   */
  goNextPage(): void {
    if (!this.pagination.last) {
      this.currentPage += 1
      this.fetchTools()
    }
  }

  /**
   * Cambiar la paginación a la página anterior
   */
  goPreviousPage(): void {
    if (!this.pagination.first) {
      this.currentPage -= 1
      this.fetchTools()
    }
  }

  /**
   * Borrar una herramienta por su ID
   * @param id ID de la herramienta a borrar
   */
  eraseTool(id: number) {
    this.toolService.eraseToolById(id).subscribe({
      next: () => {
        alert("Borrado correctamente!")
        this.fetchTools()
      },
      error: () => {
        alert("Ha ocurrido un error!")
      }
    })
  }
}
