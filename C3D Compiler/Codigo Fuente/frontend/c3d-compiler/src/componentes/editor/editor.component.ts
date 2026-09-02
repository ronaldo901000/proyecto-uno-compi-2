import { Component, ElementRef, ViewChild, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { Subscription, Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { ArbolTrabajoService } from '../../servicios/arbol-trabajo/ArbolTrabajo.service';
import { NodoArchivo } from '../../modelos/nodo-archivo/NodoArchivo';
import { ColoreadoService } from '../../servicios/coloreado/Coloreado.service';
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

  // guarda el archivo que ya ha sido coloreado antes
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
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    this.subSeleccion = this.arbolService.nodoSeleccionado$.subscribe(nodo => {
      if (nodo && nodo.tipo === 'archivo') {
        this.archivoActivo = nodo;
        this.contenido = nodo.contenido || '';
        this.actualizarLineas();

        if (this.esArchivoY()) {
          const cacheado = this.cacheColoreado.get(nodo);
          if (cacheado) {
            this.htmlColoreado = cacheado;
          } else {
            this.mostrarTextoPlano();
            this.solicitarColoreadoBackend(this.contenido);
          }
        } else {
          this.htmlColoreado = '';
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
    if (this.subSeleccion) {
      this.subSeleccion.unsubscribe();
    }
    if (this.subColoreado) {
      this.subColoreado.unsubscribe();
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
    this.contenido = nuevoContenido;
    this.actualizarLineas();
    if (event?.target) {
      this.actualizarPosicionCursor(event.target as HTMLTextAreaElement);
    }
    if (this.archivoActivo) {
      this.archivoActivo.contenido = nuevoContenido;
    }

    if (this.esArchivoY()) {
      this.mostrarTextoPlano();
    }

    this.procesarColoreadoSiEsModuloY();
  }

  public manejarTabulacion(event: KeyboardEvent): void {
    if (event.key === 'Tab') {
      event.preventDefault();
      const textarea = event.target as HTMLTextAreaElement;
      const start = textarea.selectionStart;
      const end = textarea.selectionEnd;
      const tabulacion = '    ';

      this.contenido =
        this.contenido.substring(0, start) +
        tabulacion +
        this.contenido.substring(end);

      this.actualizarLineas();
      if (this.archivoActivo) {
        this.archivoActivo.contenido = this.contenido;
      }

      if (this.esArchivoY()) {
        this.mostrarTextoPlano();
      }

      setTimeout(() => {
        textarea.selectionStart = textarea.selectionEnd = start + tabulacion.length;
        this.actualizarPosicionCursor(textarea);
        this.procesarColoreadoSiEsModuloY();
      }, 0);
    }
  }

  private mostrarTextoPlano(): void {
    this.htmlColoreado = this.sanitizer.bypassSecurityTrustHtml(
      this.escaparHtml(this.contenido) + '&nbsp;'
    );
  }

  private procesarColoreadoSiEsModuloY(): void {
    if (this.esArchivoY()) {
      this.textoSubject.next(this.contenido);
    } else {
      this.htmlColoreado = '';
    }
  }

  private solicitarColoreadoBackend(texto: string): void {
    const nodoDestino = this.archivoActivo;

    this.coloreadoService.obtenerInfoColor({ texto }).subscribe({
      next: (tokens: ColorToken[]) => {
        const htmlCrudo = this.construirHtmlColoreado(texto, tokens);
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

  private construirHtmlColoreado(texto: string, tokens: ColorToken[]): string {
    if (!tokens || tokens.length === 0) return this.escaparHtml(texto);

    tokens.sort((a, b) => a.inicio - b.inicio);

    let html = '';
    let ultimoIndice = 0;

    for (const t of tokens) {
      if (t.inicio < ultimoIndice) continue;

      if (t.inicio > ultimoIndice) {
        html += this.escaparHtml(texto.substring(ultimoIndice, t.inicio));
      }

      const valorToken = texto.substring(t.inicio, t.fin + 1);
      html += `<span style="color: ${t.color}">${this.escaparHtml(valorToken)}</span>`;

      ultimoIndice = t.fin + 1;
    }

    if (ultimoIndice < texto.length) {
      html += this.escaparHtml(texto.substring(ultimoIndice));
    }

    return html + '&nbsp;';
  }

  private escaparHtml(str: string): string {
    return str
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#039;');
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

  public esArchivoY(): boolean {
    return this.archivoActivo?.extension?.trim().toLowerCase() === 'y';
  }
}