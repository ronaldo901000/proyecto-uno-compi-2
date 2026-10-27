import { Component, OnInit, output, signal } from '@angular/core';
import { Cuarteta } from '../../modelos/cuarteta/cuarteta';
import { RespuestaCompilacionService } from '../../servicios/respuesta-compilacion/RespuestaCompilacion.service';

@Component({
  selector: 'app-cuartetas-modal',
  imports: [],
  templateUrl: './cuartetas-modal.component.html',
  styleUrl: './cuartetas-modal.component.css',
})
export class CuartetasModalComponent implements OnInit {
  cuartetas = signal<Cuarteta[]>([]);

  cerrar = output<void>();

  constructor(private resService: RespuestaCompilacionService) { }

  ngOnInit(): void {
    this.cargarCuartetas();
  }

  cargarCuartetas() {
    const o = ['FIJAR_VTABLE', 'PUNTERO_INICIO', 'LLAMADA_VIRTUAL'];
    this.cuartetas.set(
      (this.resService.getRespuesta()?.cuartetas || [])
        .filter(c => !o.includes(c.operador))
    );
  }

  onCerrar() {
    this.cerrar.emit();
  }
}