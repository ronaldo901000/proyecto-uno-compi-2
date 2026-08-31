import { Component, ElementRef, ViewChild, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { ArbolTrabajoService } from '../../servicios/arbol-trabajo/ArbolTrabajo.service';
import { NodoArchivo } from '../../modelos/nodo-archivo/NodoArchivo';

@Component({
  selector: 'app-editor',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.css'
})
export class EditorComponent implements OnInit, OnDestroy {
  public arbolService = inject(ArbolTrabajoService);
  private subSeleccion!: Subscription;

  archivoActivo: NodoArchivo | null = null;
  contenido: string = '';
  lineas: number[] = [1];
  lineaActual: number = 1;
  columnaActual: number = 1;

  @ViewChild('lineCounter') lineCounterRef!: ElementRef<HTMLDivElement>;

  ngOnInit(): void {
    // Escuchar el nodo activo en el explorador
    this.subSeleccion = this.arbolService.nodoSeleccionado$.subscribe(nodo => {
      if (nodo && nodo.tipo === 'archivo') {
        this.archivoActivo = nodo;
        // Si el nodo incluye contenido almacenado lo carga, de lo contrario inicia vacio
        this.contenido = nodo.contenido || '';
        this.actualizarLineas();
      } else {
        // Si es una carpeta o no hay nada seleccionado, ocultar el archivo activo
        this.archivoActivo = null;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subSeleccion) {
      this.subSeleccion.unsubscribe();
    }
  }

  private actualizarLineas(): void {
  
    const totalLineas = this.contenido.split('\n').length;
    this.lineas = Array.from({ length: Math.max(1, totalLineas) }, (_, i) => i + 1);
  }

  public sincronizarScroll(event: Event): void {
    const textarea = event.target as HTMLTextAreaElement;
    if (this.lineCounterRef?.nativeElement) {
      this.lineCounterRef.nativeElement.scrollTop = textarea.scrollTop;
    }
  }



  public actualizarPosicionCursor(textarea: HTMLTextAreaElement): void {
    const posicionCursor = textarea.selectionStart;
    const textoHastaCursor = this.contenido.substring(0, posicionCursor);

    const lineasHastaCursor = textoHastaCursor.split('\n');

    this.lineaActual = lineasHastaCursor.length;

    this.columnaActual = lineasHastaCursor[lineasHastaCursor.length - 1].length + 1;
  }


  onContenidoCambia(nuevoContenido: string, event?: Event) {
    this.contenido = nuevoContenido;
    this.actualizarLineas();
    if (event?.target) {
      this.actualizarPosicionCursor(event.target as HTMLTextAreaElement);
    }
    if (this.archivoActivo) {
      this.archivoActivo.contenido = nuevoContenido;
    }
  }


  public manejarTabulacion(event: KeyboardEvent): void {
    if (event.key === 'Tab') {
      event.preventDefault();
      const textarea = event.target as HTMLTextAreaElement;
      const start = textarea.selectionStart;
      const end = textarea.selectionEnd;
      const tabulacion = '\t';

      this.contenido =
        this.contenido.substring(0, start) +
        tabulacion +
        this.contenido.substring(end);

      this.actualizarLineas();
      if (this.archivoActivo) {
        this.archivoActivo.contenido = this.contenido;
      }

      setTimeout(() => {
        textarea.selectionStart = textarea.selectionEnd = start + tabulacion.length;
        this.actualizarPosicionCursor(textarea);
      }, 0);
    }
  }

  public obtenerIconoEditor(extension?: string): string {
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