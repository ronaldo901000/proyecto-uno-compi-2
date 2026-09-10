import { Component, ElementRef, ViewChild, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { Subscription, Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { ArbolTrabajoService } from '../../servicios/arbol-trabajo/ArbolTrabajo.service';
import { NodoArchivo } from '../../modelos/nodo-archivo/NodoArchivo';
import { ColoreadoService } from '../../servicios/coloreado/Coloreado.service';
import { IndentacionService } from '../../servicios/indentacion/Indentacion.service';
import { ColorToken } from '../../modelos/color-token/ColorToken';

@Component({
  selector: 'app-editor',
  imports: [CommonModule],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.css'
})
export class EditorComponent implements OnInit, OnDestroy {
  public arbolService = inject(ArbolTrabajoService);
  private subSeleccion!: Subscription;

  private textoSubject = new Subject<string>();
  private subColoreado!: Subscription;

  private cacheColoreado = new Map<NodoArchivo, SafeHtml>();

  archivoActivo: NodoArchivo | null = null;
  contenido: string = '';
  lineas: number[] = [1];
  lineaActual: number = 1;
  columnaActual: number = 1;
  htmlColoreado: SafeHtml = '';

  @ViewChild('lineCounter') lineCounterRef!: ElementRef<HTMLDivElement>;
  @ViewChild('highlightArea') highlightAreaRef!: ElementRef<HTMLDivElement>;

  constructor(
    private coloreadoService: ColoreadoService,
    private indentacionService: IndentacionService,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    this.subSeleccion = this.arbolService.nodoSeleccionado$.subscribe(nodo => {
      if (nodo && nodo.tipo === 'archivo') {
        this.archivoActivo = nodo;
        this.contenido = nodo.contenido || '';
        this.actualizarLineas();

        const cacheado = this.cacheColoreado.get(nodo);
        if (cacheado) {
          this.htmlColoreado = cacheado;
        } else {
          this.mostrarTextoPlano();
          this.solicitarColoreadoBackend(this.contenido);
        }
      } else {
        this.archivoActivo = null;
        this.htmlColoreado = '';
      }
    });

    this.subColoreado = this.textoSubject.pipe(
      debounceTime(300)
    ).subscribe(texto => {
      this.solicitarColoreadoBackend(texto);
    });
  }

  ngOnDestroy(): void {
    this.subSeleccion?.unsubscribe();
    this.subColoreado?.unsubscribe();
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
    if (this.highlightAreaRef?.nativeElement) {
      this.highlightAreaRef.nativeElement.scrollTop = textarea.scrollTop;
      this.highlightAreaRef.nativeElement.scrollLeft = textarea.scrollLeft;
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
    const textarea = event?.target as HTMLTextAreaElement | undefined;
    const cursorAntes = textarea?.selectionStart ?? null;

    const contenidoNormalizado = this.indentacionService.normalizar(nuevoContenido);

    let nuevoCursor = cursorAntes;
    if (textarea && cursorAntes !== null && contenidoNormalizado !== nuevoContenido) {
      nuevoCursor = this.indentacionService.recalcularCursor(
        nuevoContenido,
        contenidoNormalizado,
        cursorAntes
      );
    }

    this.contenido = contenidoNormalizado;
    this.actualizarLineas();

    if (this.archivoActivo) {
      this.archivoActivo.contenido = this.contenido;
    }

    this.mostrarTextoPlano();
    this.procesarColoreado();

    if (textarea && nuevoCursor !== null) {
      setTimeout(() => {
        textarea.value = this.contenido;
        textarea.selectionStart = textarea.selectionEnd = nuevoCursor!;
        this.actualizarPosicionCursor(textarea);
      }, 0);
    } else if (textarea) {
      this.actualizarPosicionCursor(textarea);
    }
  }

  public manejarTabulacion(event: KeyboardEvent): void {
    if (event.key === 'Tab') {
      event.preventDefault();
      const textarea = event.target as HTMLTextAreaElement;
      const start = textarea.selectionStart;
      const end = textarea.selectionEnd;

      const contenidoConTab =
        this.contenido.substring(0, start) +
        '\t' +
        this.contenido.substring(end);

      this.contenido = this.indentacionService.normalizar(contenidoConTab);
      this.actualizarLineas();
      if (this.archivoActivo) {
        this.archivoActivo.contenido = this.contenido;
      }

      this.mostrarTextoPlano();

      const nuevoCursor = start + 4;

      setTimeout(() => {
        textarea.value = this.contenido;
        textarea.selectionStart = textarea.selectionEnd = nuevoCursor;
        this.actualizarPosicionCursor(textarea);
        this.procesarColoreado();
      }, 0);
    }
  }

  private mostrarTextoPlano(): void {
    this.htmlColoreado = this.sanitizer.bypassSecurityTrustHtml(
      this.coloreadoService.escaparHtml(this.contenido) + '&nbsp;'
    );
  }

  private procesarColoreado(): void {
    this.textoSubject.next(this.contenido);
  }

  private solicitarColoreadoBackend(texto: string): void {
    const nodoDestino = this.archivoActivo;
    const opcion = nodoDestino?.extension;

    if (opcion) {
      this.coloreadoService.obtenerInfoColor({ texto, opcion }).subscribe({
        next: (tokens: ColorToken[]) => {
          const htmlCrudo = this.coloreadoService.construirHtmlColoreado(texto, tokens);
          const htmlSeguro = this.sanitizer.bypassSecurityTrustHtml(htmlCrudo);

          if (nodoDestino) {
            this.cacheColoreado.set(nodoDestino, htmlSeguro);
          }

          if (this.archivoActivo === nodoDestino) {
            this.htmlColoreado = htmlSeguro;
          }
        },
        error: (err) => {
          console.error('Error al obtener coloreado de tokens:', err);
        }
      });
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