import { HttpClient } from "@angular/common/http";
import { Conexion } from "../../config/Conexion";
import { Injectable } from "@angular/core";
import { Texto } from "../../modelos/texto/Texto";
import { Observable } from "rxjs";
import { ColorToken } from "../../modelos/color-token/ColorToken";

@Injectable({
    providedIn: 'root'
})
export class ColoreadoService {

    private constApi = new Conexion;

    constructor(private http: HttpClient) { }

    public obtenerInfoColor(texto: Texto): Observable<ColorToken[]> {
        return this.http.post<ColorToken[]>(
            `${this.constApi.getConexionUrl()}coloreado/lenguaje-y`,
            texto
        );
    }
}