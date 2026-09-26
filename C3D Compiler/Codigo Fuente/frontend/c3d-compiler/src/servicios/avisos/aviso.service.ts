import { Injectable, signal } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class AvisoService {
  hayError = signal(false);
  mensajeError = signal('');
  hayCodigoC = signal(false);

  mostrarError(mensaje: string) {
    this.mensajeError.set(mensaje);
    this.hayError.set(true);
  }

  limpiarError() {
    this.hayError.set(false);
    this.mensajeError.set('');
  }

  resetearHayCodigo(){
    this.hayCodigoC.set(false);
  }
}