import { Component } from '@angular/core';
import { CreacionEntradaService } from '../../servicios/creacion-entrada/CreacionEntrada.service';
import { ArbolTrabajoService } from '../../servicios/arbol-trabajo/ArbolTrabajo.service';
import { AnalisisService } from '../../servicios/analisis/Analisis.service';
import { Respuesta } from '../../modelos/respuesta/Respuesta';
import { RespuestaCompilacionService } from '../../servicios/respuesta-compilacion/RespuestaCompilacion.service';
import { AvisoService } from '../../servicios/avisos/aviso.service';

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
  ) { }

  public compilar(): void {
    this.avisoService.limpiarError();
    const raiz = this.arbolService.getArbol();
    if (raiz) {
      const entrada = this.creacionEntradaService.crearArchivos(raiz);

      this.analisisService.analizar(entrada).subscribe({
        next: (resultado: Respuesta) => {
          this.respuestaCompilacionService.setRespuesta(resultado);

          if (resultado.codigoC) {
            this.descargarArchivoC(resultado.codigoC);
          }
        },
        error: (error) => {
          this.avisoService.mostrarError(error.error);
        }
      });
    }
  }

  private descargarArchivoC(codigoC: string, nombreArchivo: string = 'salida.c'): void {
    const blob = new Blob([codigoC], { type: 'text/plain' });
    const url = window.URL.createObjectURL(blob);
    const enlace = document.createElement('a');
    enlace.href = url;
    enlace.download = nombreArchivo;
    document.body.appendChild(enlace);
    enlace.click();
    document.body.removeChild(enlace);
    window.URL.revokeObjectURL(url);
  }

}
