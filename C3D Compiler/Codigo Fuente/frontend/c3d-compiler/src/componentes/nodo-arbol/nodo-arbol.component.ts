import { Component, Input, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NodoArchivo } from '../../modelos/nodo-archivo/NodoArchivo';
import { ArbolTrabajoService } from '../../servicios/arbol-trabajo/ArbolTrabajo.service';

@Component({
    selector: 'app-nodo-arbol',
    imports: [CommonModule, NodoArbolComponent],
    templateUrl: './nodo-arbol.component.html',
    styleUrl: './nodo-arbol.component.css'
})
export class NodoArbolComponent {
  @Input({ required: true }) nodo!: NodoArchivo;

  public arbolService = inject(ArbolTrabajoService);

  desplegado: boolean = true;

  public toggleDespliegue(event: Event): void {
    event.stopPropagation();
    if (this.nodo.tipo === 'carpeta') {
      this.desplegado = !this.desplegado;
    }
  }

  public seleccionar(event: Event): void {
    event.stopPropagation();
    this.arbolService.seleccionarNodo(this.nodo);
  }
  public obtenerIconoArchivo(extension?: string): string {
    switch (extension?.trim().toLowerCase()) {
      case 'y':
        return 'bi-filetype-yml text-danger'; 
      case 'z':
        return 'bi-file-earmark-binary text-primary'; 
      case 'pig':
        return 'bi-file-earmark-code text-warning'; 
      default:
        return 'bi-file-earmark-text text-info'; 
    }
  }
}