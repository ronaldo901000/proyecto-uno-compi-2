import { Injectable } from "@angular/core";
import { Archivo } from "../../modelos/archivo/Archivo";
import { Entrada } from "../../modelos/entrada/Entrada";
import { NodoArchivo } from "../../modelos/nodo-archivo/NodoArchivo";
import { ArbolTrabajoService } from "../arbol-trabajo/ArbolTrabajo.service";

@Injectable({
    providedIn: 'root'
})

export class CreacionEntradaService {

    constructor(private arbolTrabajoService: ArbolTrabajoService) { }

    public crearArchivos(raiz: NodoArchivo): Entrada {
        const listaArchivos: Archivo[] = [];
        this.recorrerNodo(raiz, listaArchivos);

        const entrada: Entrada = {
            archivos: listaArchivos
        };

        return entrada;
    }

    private recorrerNodo(nodo: NodoArchivo, acumulador: Archivo[]): void {
        if (!nodo) return;

        //Si es un archivo se tranforma al modelo archivo
        if (nodo.tipo === 'archivo') {
            acumulador.push({
                ruta: nodo.ruta,
                nombre: nodo.nombre,
                contenido: this.arbolTrabajoService.getContenidoArchivo(nodo.ruta) ?? '',
                extension: nodo.extension ?? ''
            });
        }

        // Si es una carpeta y tiene hijos, procesar cada uno
        if (nodo.tipo === 'carpeta' && nodo.hijos && nodo.hijos.length > 0) {
            for (const hijo of nodo.hijos) {
                this.recorrerNodo(hijo, acumulador);
            }
        }
    }
}