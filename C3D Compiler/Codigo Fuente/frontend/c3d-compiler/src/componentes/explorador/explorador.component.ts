import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalArchivosComponent } from '../modal-archivos/modal-archivos.component';
import { ExtensionArchivo } from '../../modelos/extension-archivo/ExtensionArchivo';
import { TipoNodo } from '../../modelos/tipo-nodo/TipoNodo';
import { ArbolTrabajoService } from '../../servicios/arbol-trabajo/ArbolTrabajo.service';
import { NodoArbolComponent } from '../nodo-arbol/nodo-arbol.component';

@Component({
    selector: 'app-explorador',
    imports: [CommonModule, ModalArchivosComponent, NodoArbolComponent],
    templateUrl: './explorador.component.html',
    styleUrl: './explorador.component.css'
})
export class ExploradorComponent {

  modalVisible: boolean = false;
  tipoCreacion: TipoNodo = 'archivo';
  modalError: string = '';

  constructor(public arbolService: ArbolTrabajoService) { }

  public abrirModal(tipo: TipoNodo): void {
    this.tipoCreacion = tipo;
    this.modalVisible = true;
  }

  public cerrarModal(): void {
    this.modalVisible = false;
    this.modalError = ''
  }

  public procesarCreacion(datos: { nombre: string; extension: ExtensionArchivo }): void {

    if (!datos.nombre) {
      this.modalError = 'El nombre no puede estar vacio.';
    }

    if (!/^[a-zA-Z0-9_\-]+$/.test(datos.nombre)) {
      this.modalError = 'Nombre invalido: solo letras, números, guiones y guiones bajos.';
      return;
    }

    const respuesta = this.arbolService.agregarNodo(
      datos.nombre,
      this.tipoCreacion,
      datos.extension
    );

    if (respuesta.exito) {
      this.cerrarModal();
      console.log('Creando:', this.tipoCreacion, datos);
    } else {
      this.modalError = respuesta.mensaje;
    }



  }

  public abrirModalProyecto(): void {
    const nombre = prompt('Ingresa el nombre del nuevo proyecto:');
    if (nombre && nombre.trim() !== '') {
      this.arbolService.crearNuevoProyecto(nombre.trim());
    }
  }

  public eliminar(): void {
    const nodo = this.arbolService.getNodoSeleccionado();
    if (!nodo) return;

    const tipoTexto = nodo.tipo === 'carpeta' ? 'la carpeta' : 'el archivo';
    const mensajeConfirmacion = `¿Estás seguro de que deseas eliminar ${tipoTexto} "${nodo.nombre}"?`;

    if (confirm(mensajeConfirmacion)) {
      const respuesta = this.arbolService.eliminarNodoSeleccionado();
      if (!respuesta.exito) {
        alert(respuesta.mensaje);
      }
    }
  }
}