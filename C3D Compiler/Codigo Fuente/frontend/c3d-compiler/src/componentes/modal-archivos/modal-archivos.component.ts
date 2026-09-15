import { Component, EventEmitter, Input, Output, OnChanges, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ExtensionArchivo } from '../../modelos/extension-archivo/ExtensionArchivo';
import { TipoNodo } from '../../modelos/tipo-nodo/TipoNodo';

@Component({
  selector: 'app-modal-archivos',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './modal-archivos.component.html',
  styleUrl: './modal-archivos.component.css'
})
export class ModalArchivosComponent implements OnChanges {
  // Entradas de Configuración
  @Input() modo: 'crear' | 'editar' = 'crear';
  @Input() tipo: TipoNodo = 'archivo';
  @Input() error: string = '';

  // Entradas de Valores Iniciales (Para cuando se está editando)
  @Input() nombreInicial: string = '';
  @Input() extensionInicial: ExtensionArchivo = 'y';

  // Eventos de Salida
  @Output() cerrar = new EventEmitter<void>();
  @Output() confirmar = new EventEmitter<{ nombre: string; extension: ExtensionArchivo }>();


  nombre: string = '';
  extension: ExtensionArchivo = 'y';

  opcionesExtension: ExtensionArchivo[] = ['y', 'z', 'pig'];


  ngOnChanges(changes: SimpleChanges): void {
    if (this.modo === 'editar') {
      this.nombre = this.nombreInicial;
      this.extension = this.extensionInicial || 'y';
    } else {
      this.resetFormulario();
    }
  }

  private resetFormulario(): void {
    this.nombre = '';
    this.extension = 'y';
  }

  onCerrar(): void {
    this.resetFormulario();
    this.cerrar.emit();
  }

  onConfirmar(): void {
    if (!this.nombre || !this.nombre.trim()) return;

    this.confirmar.emit({
      nombre: this.nombre.trim(),
      extension: this.extension
    });
  }
}