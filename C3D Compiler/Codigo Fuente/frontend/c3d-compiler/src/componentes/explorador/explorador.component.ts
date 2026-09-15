import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalArchivosComponent } from '../modal-archivos/modal-archivos.component';
import { ExtensionArchivo } from '../../modelos/extension-archivo/ExtensionArchivo';
import { TipoNodo } from '../../modelos/tipo-nodo/TipoNodo';
import { ArbolTrabajoService } from '../../servicios/arbol-trabajo/ArbolTrabajo.service';
import { NodoArbolComponent } from '../nodo-arbol/nodo-arbol.component';

@Component({
  selector: 'app-explorador',
  standalone: true,
  imports: [CommonModule, ModalArchivosComponent, NodoArbolComponent],
  templateUrl: './explorador.component.html',
  styleUrl: './explorador.component.css'
})
export class ExploradorComponent {

  modalVisible: boolean = false;
  tipoCreacion: TipoNodo = 'archivo';
  modalError: string = '';

  // Estado para controlar el comportamiento del modal
  modoAccion: 'crear' | 'editar' = 'crear';
  nombreInicial: string = '';
  extensionInicial: ExtensionArchivo = 'y';

  // Estado para deshabilitar el botón mientras se importan/leen archivos
  public importando: boolean = false;

  constructor(public arbolService: ArbolTrabajoService) { }

  // Abrir modal para CREAR archivo o carpeta
  public abrirModalCrear(tipo: TipoNodo): void {
    this.modoAccion = 'crear';
    this.tipoCreacion = tipo;
    this.nombreInicial = '';
    this.extensionInicial = 'y';
    this.modalError = '';
    this.modalVisible = true;
  }

  // Abrir modal para EDITAR/RENOMBRAR el nodo seleccionado
  public abrirModalEdicion(): void {
    const nodo = this.arbolService.getNodoSeleccionado();
    if (!nodo) return;

    this.modoAccion = 'editar';
    this.tipoCreacion = nodo.tipo;
    this.nombreInicial = nodo.nombre;
    this.extensionInicial = nodo.extension ?? 'y';
    this.modalError = '';
    this.modalVisible = true;
  }

  public cerrarModal(): void {
    this.modalVisible = false;
    this.modalError = '';
  }

  // Procesa la confirmación del modal (tanto para creación como para edición)
  public procesarAccionModal(datos: { nombre: string; extension: ExtensionArchivo }): void {
    const nombreLimpio = datos.nombre.trim();

    if (!nombreLimpio) {
      this.modalError = 'El nombre no puede estar vacío.';
      return;
    }

    if (!/^[a-zA-Z0-9_\-]+$/.test(nombreLimpio)) {
      this.modalError = 'Nombre inválido: solo letras, números, guiones y guiones bajos.';
      return;
    }

    let respuesta;

    if (this.modoAccion === 'crear') {
      respuesta = this.arbolService.agregarNodo(
        nombreLimpio,
        this.tipoCreacion,
        datos.extension
      );
    } else {
      respuesta = this.arbolService.renombrarNodoSeleccionado(nombreLimpio);
    }

    if (respuesta.exito) {
      this.cerrarModal();
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

  // Handler del input webkitdirectory: importa la carpeta seleccionada (con contenido)
  public async onCarpetaSeleccionada(event: Event): Promise<void> {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;

    if (this.arbolService.getArbol() && !confirm('Esto reemplazará el proyecto actual. ¿Continuar?')) {
      input.value = '';
      return;
    }

    this.importando = true;
    try {
      const respuesta = await this.arbolService.importarProyectoDesdeArchivos(input.files);
      if (!respuesta.exito) {
        alert(respuesta.mensaje);
      }
    } catch (error) {
      console.error('Error al importar el proyecto:', error);
      alert('Ocurrió un error inesperado al importar el proyecto.');
    } finally {
      this.importando = false;
      input.value = ''; // permite reimportar la misma carpeta después
    }
  }
}