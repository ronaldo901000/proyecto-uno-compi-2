import { Injectable } from "@angular/core";
import { BehaviorSubject, map, Observable } from "rxjs";
import { NodoArchivo } from "../../modelos/nodo-archivo/NodoArchivo";
import { TipoNodo } from "../../modelos/tipo-nodo/TipoNodo";
import { ExtensionArchivo } from "../../modelos/extension-archivo/ExtensionArchivo";
import { RespuestaAccionArchivo } from "../../modelos/respuesta-creacion/RespuestaCreacion";

@Injectable({ providedIn: 'root' })
export class ArbolTrabajoService {

    private static readonly EXTENSIONES_PERMITIDAS: ExtensionArchivo[] = ['y', 'z', 'pig'];
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

    private contenidosArchivos = new Map<string, string>();

    public getContenidoArchivo(ruta: string): string | undefined {
        return this.contenidosArchivos.get(ruta);
    }

    public actualizarContenidoArchivo(ruta: string, nuevoContenido: string): void {
        this.contenidosArchivos.set(ruta, nuevoContenido);
    }

    public getArbol(): NodoArchivo | null {
        return this.arbolSubject.value;
    }

    public getNodoSeleccionado(): NodoArchivo | null {
        return this.nodoSeleccionadoSubject.value;
    }

    public crearNuevoProyecto(nombreProyecto: string): void {
        const raizProyecto: NodoArchivo = {
            ruta: nombreProyecto.trim(),
            nombre: nombreProyecto.trim(),
            tipo: 'carpeta',
            hijos: [],
            estaSeleccionado: false
        };

        this.contenidosArchivos.clear();
        this.arbolSubject.next(raizProyecto);
    }

    public cerrarProyecto(): void {
        this.arbolSubject.next(null);
        this.nodoSeleccionadoSubject.next(null);
        this.contenidosArchivos.clear();
    }

    public agregarNodo(nombre: string, tipo: TipoNodo, extension: ExtensionArchivo): RespuestaAccionArchivo {
        const arbolActual = this.arbolSubject.value;
        const nodoSeleccionado = this.nodoSeleccionadoSubject.value;

        if (!arbolActual || !nodoSeleccionado || nodoSeleccionado.tipo !== 'carpeta') {
            return {
                exito: false,
                mensaje: 'No se puede crear el recurso: debes seleccionar una carpeta destino.'
            };
        }

        const yaExiste = nodoSeleccionado.hijos?.some(hijo =>
            hijo.nombre.toLowerCase() === nombre.trim().toLowerCase() && hijo.extension === extension
        );

        if (yaExiste) {
            return {
                exito: false,
                mensaje: `Ya existe un elemento llamado "${nombre.trim()}" en esta carpeta.`
            };
        }

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

        const insertado = this.insertarEnArbolRecursivo(arbolActual, nodoSeleccionado.ruta, nuevoNodo);

        if (insertado) {
         
            if (tipo === 'archivo') {
                this.contenidosArchivos.set(nuevaRuta, '');
            }
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

        if (nodoSeleccionado.ruta === arbolActual.ruta) {
            this.cerrarProyecto();
            return { exito: true, mensaje: 'Proyecto cerrado y eliminado correctamente.' };
        }

        this.eliminarContenidosRecursivo(nodoSeleccionado);

        const eliminado = this.eliminarEnArbolRecursivo(arbolActual, nodoSeleccionado.ruta);

        if (eliminado) {
            this.seleccionarNodo(arbolActual);
            this.arbolSubject.next({ ...arbolActual });
            return { exito: true, mensaje: 'Elemento eliminado correctamente.' };
        }

        return { exito: false, mensaje: 'No se pudo encontrar el elemento en el arbol.' };
    }

    private eliminarEnArbolRecursivo(padreActual: NodoArchivo, rutaAEliminar: string): boolean {
        if (!padreActual.hijos || padreActual.hijos.length === 0) {
            return false;
        }

        const indice = padreActual.hijos.findIndex(hijo => hijo.ruta === rutaAEliminar);
        if (indice !== -1) {
            padreActual.hijos.splice(indice, 1);
            return true;
        }

        for (const hijo of padreActual.hijos) {
            if (hijo.tipo === 'carpeta') {
                const exito = this.eliminarEnArbolRecursivo(hijo, rutaAEliminar);
                if (exito) return true;
            }
        }

        return false;
    }

    private eliminarContenidosRecursivo(nodo: NodoArchivo): void {
        if (nodo.tipo === 'archivo') {
            this.contenidosArchivos.delete(nodo.ruta);
            return;
        }
        if (nodo.hijos) {
            nodo.hijos.forEach(hijo => this.eliminarContenidosRecursivo(hijo));
        }
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


    public renombrarNodoSeleccionado(nuevoNombre: string): RespuestaAccionArchivo {
        const arbolActual = this.arbolSubject.value;
        const nodoSeleccionado = this.nodoSeleccionadoSubject.value;

        if (!arbolActual || !nodoSeleccionado) {
            return { exito: false, mensaje: 'No hay ningún elemento seleccionado para renombrar.' };
        }

        const nuevoNombreTrim = nuevoNombre.trim();

        if (nodoSeleccionado.nombre === nuevoNombreTrim) {
            return { exito: true, mensaje: 'El nombre no ha cambiado.' };
        }

        if (nodoSeleccionado.ruta === arbolActual.ruta) {
            const rutaAnteriorRaiz = nodoSeleccionado.ruta;

            nodoSeleccionado.nombre = nuevoNombreTrim;
            nodoSeleccionado.ruta = nuevoNombreTrim;

           
            const mapeoRutas = new Map<string, string>();
            this.recolectarMapeoRutas(nodoSeleccionado, rutaAnteriorRaiz, mapeoRutas);

            this.actualizarRutasHijosRecursivo(nodoSeleccionado);
            this.migrarContenidos(mapeoRutas);

            const nuevoArbolRaiz: NodoArchivo = { ...nodoSeleccionado };

            this.nodoSeleccionadoSubject.next(nuevoArbolRaiz);
            this.arbolSubject.next(nuevoArbolRaiz);

            return { exito: true, mensaje: 'Proyecto renombrado exitosamente.' };
        }

        const padre = this.buscarPadreRecursivo(arbolActual, nodoSeleccionado.ruta);

        if (!padre || !padre.hijos) {
            return { exito: false, mensaje: 'No se pudo localizar el contenedor del elemento.' };
        }

        const yaExiste = padre.hijos.some(hijo =>
            hijo !== nodoSeleccionado &&
            hijo.nombre.toLowerCase() === nuevoNombreTrim.toLowerCase() &&
            hijo.extension === nodoSeleccionado.extension
        );

        if (yaExiste) {
            return {
                exito: false,
                mensaje: `Ya existe un elemento llamado "${nuevoNombreTrim}" en esta carpeta.`
            };
        }

        const rutaAnterior = nodoSeleccionado.ruta;
        const rutaPadre = padre.ruta === '/' ? '' : padre.ruta;
        const ext = nodoSeleccionado.tipo === 'archivo' && nodoSeleccionado.extension ? `.${nodoSeleccionado.extension}` : '';
        const nuevaRuta = `${rutaPadre}/${nuevoNombreTrim}${ext}`;


        const mapeoRutas = new Map<string, string>();
        this.recolectarMapeoRutas(nodoSeleccionado, rutaAnterior, mapeoRutas, nuevaRuta);

        nodoSeleccionado.nombre = nuevoNombreTrim;
        nodoSeleccionado.ruta = nuevaRuta;

        if (nodoSeleccionado.tipo === 'carpeta') {
            this.actualizarRutasHijosRecursivo(nodoSeleccionado);
        }

        this.migrarContenidos(mapeoRutas);

        this.nodoSeleccionadoSubject.next({ ...nodoSeleccionado });
        this.arbolSubject.next({ ...arbolActual });

        return { exito: true, mensaje: 'Elemento renombrado exitosamente.' };
    }

    private recolectarMapeoRutas(
        nodo: NodoArchivo,
        rutaViejaBase: string,
        mapeoRutas: Map<string, string>,
        rutaNuevaBase?: string
    ): void {
        const nuevaBase = rutaNuevaBase ?? nodo.ruta;

        if (nodo.tipo === 'archivo') {
            mapeoRutas.set(nodo.ruta, nuevaBase);
            return;
        }

        if (nodo.hijos) {
            for (const hijo of nodo.hijos) {
                const ext = hijo.tipo === 'archivo' && hijo.extension ? `.${hijo.extension}` : '';
                const rutaNuevaHijo = `${nuevaBase}/${hijo.nombre}${ext}`;
                this.recolectarMapeoRutas(hijo, hijo.ruta, mapeoRutas, rutaNuevaHijo);
            }
        }
    }


    private migrarContenidos(mapeoRutas: Map<string, string>): void {
        mapeoRutas.forEach((rutaNueva, rutaVieja) => {
            if (rutaVieja === rutaNueva) return;
            const contenido = this.contenidosArchivos.get(rutaVieja);
            if (contenido !== undefined) {
                this.contenidosArchivos.delete(rutaVieja);
                this.contenidosArchivos.set(rutaNueva, contenido);
            }
        });
    }

    private actualizarRutasHijosRecursivo(nodoPadre: NodoArchivo): void {
        if (!nodoPadre.hijos || nodoPadre.hijos.length === 0) return;

        for (const hijo of nodoPadre.hijos) {
            const ext = hijo.tipo === 'archivo' && hijo.extension ? `.${hijo.extension}` : '';
            hijo.ruta = `${nodoPadre.ruta}/${hijo.nombre}${ext}`;
            if (hijo.tipo === 'carpeta') {
                this.actualizarRutasHijosRecursivo(hijo);
            }
        }
    }

    private buscarPadreRecursivo(padreActual: NodoArchivo, rutaHijo: string): NodoArchivo | null {
        if (!padreActual.hijos) return null;

        if (padreActual.hijos.some(hijo => hijo.ruta === rutaHijo)) {
            return padreActual;
        }

        for (const hijo of padreActual.hijos) {
            if (hijo.tipo === 'carpeta') {
                const padreEncontrado = this.buscarPadreRecursivo(hijo, rutaHijo);
                if (padreEncontrado) return padreEncontrado;
            }
        }

        return null;
    }



    

    public async importarProyectoDesdeArchivos(archivos: FileList): Promise<RespuestaAccionArchivo> {
        if (archivos.length === 0) {
            return { exito: false, mensaje: 'No se seleccionó ninguna carpeta.' };
        }

        const primerArchivo = archivos[0] as File & { webkitRelativePath: string };
        const nombreRaiz = primerArchivo.webkitRelativePath.split('/')[0];

        const raiz: NodoArchivo = {
            ruta: nombreRaiz,
            nombre: nombreRaiz,
            tipo: 'carpeta',
            hijos: [],
            estaSeleccionado: false
        };


        this.contenidosArchivos.clear();

        let archivosImportados = 0;
        const tareasLectura: Promise<void>[] = [];

        for (let i = 0; i < archivos.length; i++) {
            const archivo = archivos[i] as File & { webkitRelativePath: string };

            const segmentos = archivo.webkitRelativePath.split('/').slice(1);
            if (segmentos.length === 0) continue;

            const nombreCompleto = segmentos[segmentos.length - 1];
            const extension = this.obtenerExtension(nombreCompleto);

            if (!extension || !ArbolTrabajoService.EXTENSIONES_PERMITIDAS.includes(extension)) {
                continue;
            }

            const rutaFinal = this.insertarRutaEnArbol(raiz, segmentos, extension);
            archivosImportados++;

            tareasLectura.push(
                archivo.text().then(contenido => {
                    this.contenidosArchivos.set(rutaFinal, contenido);
                }).catch(error => {
                    console.error(`Error leyendo el archivo "${archivo.webkitRelativePath}":`, error);
                    this.contenidosArchivos.set(rutaFinal, '');
                })
            );
        }

        if (archivosImportados === 0) {
            return { exito: false, mensaje: 'La carpeta no contiene archivos .y, .z o .pig.' };
        }

        await Promise.all(tareasLectura);

        this.limpiarCarpetasVacias(raiz);
        this.arbolSubject.next(raiz);
        this.seleccionarNodo(raiz);

        return {
            exito: true,
            mensaje: `Proyecto importado (${archivosImportados} archivo(s)).`
        };
    }

    private obtenerExtension(nombreArchivo: string): ExtensionArchivo | null {
        const partes = nombreArchivo.split('.');
        if (partes.length < 2) return null;
        return partes[partes.length - 1].toLowerCase() as ExtensionArchivo;
    }


    private insertarRutaEnArbol(raiz: NodoArchivo, segmentos: string[], extension: ExtensionArchivo): string {
        let actual = raiz;

        for (let i = 0; i < segmentos.length - 1; i++) {
            const nombreCarpeta = segmentos[i];
            if (!actual.hijos) actual.hijos = [];

            let carpeta = actual.hijos.find(h => h.tipo === 'carpeta' && h.nombre === nombreCarpeta);

            if (!carpeta) {
                carpeta = {
                    ruta: `${actual.ruta}/${nombreCarpeta}`,
                    nombre: nombreCarpeta,
                    tipo: 'carpeta',
                    hijos: [],
                    estaSeleccionado: false
                };
                actual.hijos.push(carpeta);
            }
            actual = carpeta;
        }

        const nombreCompleto = segmentos[segmentos.length - 1];
        const nombreBase = nombreCompleto.slice(0, nombreCompleto.lastIndexOf('.'));
        const rutaArchivo = `${actual.ruta}/${nombreBase}.${extension}`;

        if (!actual.hijos) actual.hijos = [];
        actual.hijos.push({
            ruta: rutaArchivo,
            nombre: nombreBase,
            tipo: 'archivo',
            extension: extension,
            estaSeleccionado: false
        });

        return rutaArchivo;
    }

    private limpiarCarpetasVacias(nodo: NodoArchivo): boolean {
        if (nodo.tipo === 'archivo') return true;

        if (nodo.hijos) {
            nodo.hijos = nodo.hijos.filter(hijo => this.limpiarCarpetasVacias(hijo));
        }
        return !!nodo.hijos && nodo.hijos.length > 0;
    }
}