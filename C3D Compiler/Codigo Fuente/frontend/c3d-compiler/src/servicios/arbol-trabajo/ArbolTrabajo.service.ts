import { Injectable } from "@angular/core";
import { BehaviorSubject, map, Observable } from "rxjs";
import { NodoArchivo } from "../../modelos/nodo-archivo/NodoArchivo";
import { TipoNodo } from "../../modelos/tipo-nodo/TipoNodo";
import { ExtensionArchivo } from "../../modelos/extension-archivo/ExtensionArchivo";
import { RespuestaAccionArchivo } from "../../modelos/respuesta-creacion/RespuestaCreacion";

@Injectable({ providedIn: 'root' })
export class ArbolTrabajoService {

    private arbolSubject = new BehaviorSubject<NodoArchivo | null>(null)
    arbol$ = this.arbolSubject.asObservable();


    private nodoSeleccionadoSubject = new BehaviorSubject<NodoArchivo | null>(null);
    public nodoSeleccionado$ = this.nodoSeleccionadoSubject.asObservable();

    public hayProyecto$: Observable<boolean> = this.arbol$.pipe(
        map(arbol => arbol !== null)
    );

    public puedeCrearEnSeleccion$: Observable<boolean> = this.nodoSeleccionado$.pipe(
        map(nodo => nodo !== null && nodo.tipo === 'carpeta')
    );

    public puedeEliminarSeleccion$: Observable<boolean> = this.nodoSeleccionado$.pipe(
        map(nodo => nodo !== null)
    );

    public getArbol(): NodoArchivo | null {
        return this.arbolSubject.value;
    }

    public getNodoSeleccionado(): NodoArchivo | null {
        return this.nodoSeleccionadoSubject.value;
    }

    public crearNuevoProyecto(nombreProyecto: string): void {
        const raizProyecto: NodoArchivo = {
            ruta: '/',
            nombre: nombreProyecto.trim(),
            tipo: 'carpeta',
            hijos: [],
            estaSeleccionado: false
        };

        //agregar la carpeta como raiz del proyecto
        this.arbolSubject.next(raizProyecto);

    }

    public cerrarProyecto(): void {
        this.arbolSubject.next(null);
        this.nodoSeleccionadoSubject.next(null);
    }

    public agregarNodo(nombre: string, tipo: TipoNodo, extension: ExtensionArchivo): RespuestaAccionArchivo {
        const arbolActual = this.arbolSubject.value;
        const nodoSeleccionado = this.nodoSeleccionadoSubject.value;

        //Debe existir un proyecto y un nodo seleccionado que sea una CARPETA
        if (!arbolActual || !nodoSeleccionado || nodoSeleccionado.tipo !== 'carpeta') {
            return {
                exito: false,
                mensaje: 'No se puede crear el recurso: debes seleccionar una carpeta destino.'
            };
        }

        //Evitar duplicados con el mismo nombre en la misma carpeta
        const yaExiste = nodoSeleccionado.hijos?.some(hijo =>
            hijo.nombre.toLowerCase() === nombre.trim().toLowerCase() && hijo.extension === extension
        );

        if (yaExiste) {
            return {
                exito: false,
                mensaje: `Ya existe un elemento llamado "${nombre.trim()}" en esta carpeta.`
            };
        }

        //Construcción de la ruta
        const rutaPadre = nodoSeleccionado.ruta === '/' ? '' : nodoSeleccionado.ruta;
        const nuevaRuta = `${rutaPadre}/${nombre.trim()}${tipo === 'archivo' ? '.' + extension : ''}`;

        const nuevoNodo: NodoArchivo = {
            ruta: nuevaRuta,
            nombre: nombre.trim(),
            tipo: tipo,
            extension: extension,
            estaSeleccionado: false,
            ...(tipo === 'carpeta' && { hijos: [] })
        };

        //Inserción recursiva
        const insertado = this.insertarEnArbolRecursivo(arbolActual, nodoSeleccionado.ruta, nuevoNodo);

        if (insertado) {
            this.arbolSubject.next({ ...arbolActual });
            return {
                exito: true,
                mensaje: 'Elemento creado exitosamente.'
            };
        }

        return {
            exito: false,
            mensaje: 'Ocurrio un error inesperado al intentar insertar el elemento en la estructura.'
        };
    }

    private insertarEnArbolRecursivo(padreActual: NodoArchivo, rutaDestino: string, nuevoHijo: NodoArchivo): boolean {
        if (padreActual.ruta === rutaDestino) {
            if (!padreActual.hijos) {
                padreActual.hijos = [];
            }
            padreActual.hijos.push(nuevoHijo);
            return true;
        }

        if (padreActual.hijos && padreActual.hijos.length > 0) {
            for (const hijo of padreActual.hijos) {
                if (hijo.tipo === 'carpeta') {
                    const exito = this.insertarEnArbolRecursivo(hijo, rutaDestino, nuevoHijo);
                    if (exito) return true;
                }
            }
        }

        return false;
    }


    public eliminarNodoSeleccionado(): RespuestaAccionArchivo {
        const arbolActual = this.arbolSubject.value;
        const nodoSeleccionado = this.nodoSeleccionadoSubject.value;

        if (!arbolActual || !nodoSeleccionado) {
            return { exito: false, mensaje: 'No hay ningún elemento seleccionado para eliminar.' };
        }

        // cerrar el proyecto si la carpeta seleccionada es la raiz
        if (nodoSeleccionado.ruta === arbolActual.ruta) {
            this.cerrarProyecto();
            return { exito: true, mensaje: 'Proyecto cerrado y eliminado correctamente.' };
        }

        //Eliminar un archivo o carpeta dentro del proyecto
        const eliminado = this.eliminarEnArbolRecursivo(arbolActual, nodoSeleccionado.ruta);

        if (eliminado) {
            // Al eliminar, la carpeta raiz pasa a ser la nueva seleccion activa
            this.seleccionarNodo(arbolActual);
            // Notificar el cambio del arbol a toda la aplicación
            this.arbolSubject.next({ ...arbolActual });
            return { exito: true, mensaje: 'Elemento eliminado correctamente.' };
        }

        return { exito: false, mensaje: 'No se pudo encontrar el elemento en el arbol.' };
    }

    private eliminarEnArbolRecursivo(padreActual: NodoArchivo, rutaAEliminar: string): boolean {
        if (!padreActual.hijos || padreActual.hijos.length === 0) {
            return false;
        }

        // Verificar si el elemento a borrar es un hijo directo del nodo actual
        const indice = padreActual.hijos.findIndex(hijo => hijo.ruta === rutaAEliminar);
        if (indice !== -1) {
            padreActual.hijos.splice(indice, 1);
            return true;
        }

        // Si no esta entre los hijos directos, buscar mas profundo dentro de las subcarpetas
        for (const hijo of padreActual.hijos) {
            if (hijo.tipo === 'carpeta') {
                const exito = this.eliminarEnArbolRecursivo(hijo, rutaAEliminar);
                if (exito) return true;
            }
        }

        return false;
    }

    public seleccionarNodo(nodoASeleccionar: NodoArchivo): void {
        const arbolActual = this.arbolSubject.value;
        if (!arbolActual) return;

        this.desmarcarNodosRecursivo(arbolActual);

        nodoASeleccionar.estaSeleccionado = true;
        this.nodoSeleccionadoSubject.next(nodoASeleccionar);

        this.arbolSubject.next({ ...arbolActual });
    }

    private desmarcarNodosRecursivo(nodo: NodoArchivo): void {
        nodo.estaSeleccionado = false;
        if (nodo.hijos && nodo.hijos.length > 0) {
            nodo.hijos.forEach(hijo => this.desmarcarNodosRecursivo(hijo));
        }
    }

}