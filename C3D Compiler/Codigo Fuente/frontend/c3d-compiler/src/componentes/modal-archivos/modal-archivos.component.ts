import { Component, EventEmitter, Input, Output } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { ExtensionArchivo } from '../../modelos/extension-archivo/ExtensionArchivo';
import { TipoNodo } from '../../modelos/tipo-nodo/TipoNodo';
@Component({
    selector: 'app-modal-archivos',
    imports: [FormsModule],
    templateUrl: './modal-archivos.component.html',
    styleUrl: './modal-archivos.component.css'
})
export class ModalArchivosComponent {
  @Input() tipo: TipoNodo = 'archivo';
  @Input() error: string = '';

  @Output() cerrar = new EventEmitter<void>();
  @Output() confirmar = new EventEmitter<{ nombre: string; extension: ExtensionArchivo }>();

  nombre: string = '';
  extension: ExtensionArchivo = 'y'; 

  opcionesExtension: ExtensionArchivo[] = ['y', 'z', 'pig'];

  onCerrar() {
    this.nombre = '';
    this.extension = 'y';
    this.cerrar.emit();
  }

  onConfirmar() {
    if (!this.nombre.trim()) return;

    this.confirmar.emit({ 
      nombre: this.nombre.trim(), 
      extension: this.extension 
    });
  }
}