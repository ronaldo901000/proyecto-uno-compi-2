import { ExtensionArchivo } from "../extension-archivo/ExtensionArchivo"
import { TipoNodo } from "../tipo-nodo/TipoNodo"

export interface NodoArchivo {
    ruta: string;
    nombre:string;
    tipo:TipoNodo;
    contenido?:string;
    extension?:ExtensionArchivo;
    hijos?: NodoArchivo[];
    estaSeleccionado:boolean;
}