import { Component, ElementRef, ViewChild, effect } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormsModule } from '@angular/forms';
import { EMPTY, interval } from 'rxjs';
import { catchError, exhaustMap, filter } from 'rxjs/operators';
import { EjecucionService } from '../../servicios/ejecucion/ejecucion.service';

@Component({
  selector: 'app-consola',
  imports: [FormsModule],
  templateUrl: './consola.component.html',
})
export class ConsolaComponent {
  @ViewChild('salidaRef') salidaRef!: ElementRef<HTMLTextAreaElement>;

  entrada = '';

  constructor(public ejecucionService: EjecucionService) {
    // auto-scroll cuando cambie la salida
    effect(() => {
      this.ejecucionService.salida();
      setTimeout(() => {
        const el = this.salidaRef?.nativeElement;
        if (el) el.scrollTop = el.scrollHeight;
      });
    });

    // polling mientras haya sesión viva
    interval(700).pipe(
      filter(() => this.ejecucionService.ejecutando()),
      exhaustMap(() =>
        this.ejecucionService.salidaPendiente().pipe(catchError(() => EMPTY))),
      takeUntilDestroyed()
    ).subscribe(r => {
      if (r.salida) this.escribir(r.salida);
      if (r.terminado) this.finalizar('\n[Programa finalizado]\n');
    });
  }

  public enviar(): void {
    const texto = this.entrada;
    if (!this.ejecucionService.ejecutando() || !texto.trim()) return;

    this.entrada = '';
    this.escribir(`> ${texto}\n`);          // RARS no hace eco

    this.ejecucionService
      .enviar({ sessionId: this.ejecucionService.idSession(), texto })
      .subscribe({
        next: (r) => {
          if (r.salida) this.escribir(r.salida);
          if (r.terminado) this.finalizar('\n[Programa finalizado]\n');
        },
        error: (e) => {
          if (e.status === 404 || e.status === 410) this.finalizar();
          else this.escribir(`${e.error ?? e.message}\n`);
        },
      });
  }

  public detener(): void {
    this.ejecucionService.detener().subscribe({
      next: () => this.finalizar('\n^C Proceso detenido.\n'),
      error: () => this.finalizar(),
    });
  }

  onEnter(event: Event): void {
    const e = event as KeyboardEvent;
    if (!e.shiftKey) {
      e.preventDefault();
      this.enviar();
    }
  }

  private finalizar(mensaje?: string): void {
    if (mensaje) this.escribir(mensaje);
    this.ejecucionService.ejecutando.set(false);
    this.ejecucionService.idSession.set('');
  }

  private escribir(texto: string): void {
    this.ejecucionService.salida.update(s => s + texto);
  }
}