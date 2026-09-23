import { Injectable, signal } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class AvisoService {
  hayError = signal(false);
  mensajeError = signal('');

  mostrarError(mensaje: string) {
    this.mensajeError.set(mensaje);
    this.hayError.set(true);
  }

  limpiarError() {
    this.hayError.set(false);
    this.mensajeError.set('');
  }
}