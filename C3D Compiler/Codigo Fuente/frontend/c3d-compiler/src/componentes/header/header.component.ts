import { Component } from '@angular/core';
import { CreacionEntradaService } from '../../servicios/creacion-entrada/CreacionEntrada.service';
import { ArbolTrabajoService } from '../../servicios/arbol-trabajo/ArbolTrabajo.service';
import { AnalisisService } from '../../servicios/analisis/Analisis.service';
import { Respuesta } from '../../modelos/respuesta/Respuesta';
import { RespuestaCompilacionService } from '../../servicios/respuesta-compilacion/RespuestaCompilacion.service';
import { AvisoService } from '../../servicios/avisos/aviso.service';
import { SessionRespuesta } from '../../modelos/respuesta/sesion-respuesta';
import { EjecucionService } from '../../servicios/ejecucion/ejecucion.service';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  constructor(
    private creacionEntradaService: CreacionEntradaService,
    private arbolService: ArbolTrabajoService,
    private analisisService: AnalisisService,
    private respuestaCompilacionService: RespuestaCompilacionService,
    private avisoService: AvisoService,
    private ejecucionService: EjecucionService
  ) { }

  public compilar(): void {
    this.avisoService.limpiarError();
    this.avisoService.resetearHayCodigo();
    const raiz = this.arbolService.getArbol();
    if (!raiz) return;

    const entrada = this.creacionEntradaService.crearArchivos(raiz);

    // cerrar una sesión anterior para no dejar procesos huérfanos
    if (this.ejecucionService.ejecutando()) {
      this.ejecucionService.detener().subscribe();
    }

    this.analisisService.analizar(entrada).subscribe({
      next: (resultado: SessionRespuesta) => {
        this.respuestaCompilacionService.setRespuesta(resultado);
        this.ejecucionService.reiniciar();

        if (resultado.codigoRiscV) {
          this.avisoService.hayCodigoRisc.set(true);
        }

        // Solo hay ejecución si el análisis no tuvo errores
        if (resultado.exito && resultado.sessionId) {
          this.ejecucionService.salida.set(resultado.salida ?? '');
          if (resultado.terminado) {
            this.ejecucionService.salida.update(s => s + '\n[Programa finalizado]\n');
          } else {
            this.ejecucionService.idSession.set(resultado.sessionId);
            this.ejecucionService.ejecutando.set(true);
          }
        }
      },
      error: (error) => this.avisoService.mostrarError(error.error),
    });
  }
}
