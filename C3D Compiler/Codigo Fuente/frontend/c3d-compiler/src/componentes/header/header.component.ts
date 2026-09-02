import { Component } from '@angular/core';
import { CreacionEntradaService } from '../../servicios/creacion-entrada/CreacionEntrada.service';
import { ArbolTrabajoService } from '../../servicios/arbol-trabajo/ArbolTrabajo.service';
import { AnalisisService } from '../../servicios/analisis/Analisis.service';
import { Respuesta } from '../../modelos/respuesta/Respuesta';
import { RespuestaCompilacionService } from '../../servicios/respuesta-compilacion/RespuestaCompilacion.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  constructor(
    private creacionEntradaService: CreacionEntradaService,
    private arbolService: ArbolTrabajoService,
    private analisisService: AnalisisService,
    private respuestaCompilacionService: RespuestaCompilacionService
  ) { }

  public compilar(): void {
    const raiz = this.arbolService.getArbol();
    if (raiz) {
      const entrada = this.creacionEntradaService.crearArchivos(raiz);

      this.analisisService.analizar(entrada).subscribe({
        next:(resultado: Respuesta) =>{

          this.respuestaCompilacionService.setRespuesta(resultado);

        },
        error:(error) =>{
          console.log(error.error);
        }
      });
    }
  }

}
