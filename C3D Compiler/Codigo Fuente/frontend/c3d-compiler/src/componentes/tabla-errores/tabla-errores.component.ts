import { Component } from '@angular/core';
import { RespuestaCompilacionService } from '../../servicios/respuesta-compilacion/RespuestaCompilacion.service';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-tabla-errores',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './tabla-errores.component.html',
  styleUrl: './tabla-errores.component.css'
})
export class TablaErroresComponent {
constructor(public respuestaService:RespuestaCompilacionService){}
}
