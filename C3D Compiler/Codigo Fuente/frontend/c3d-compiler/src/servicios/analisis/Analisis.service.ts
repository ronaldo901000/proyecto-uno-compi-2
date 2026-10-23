import { HttpClient } from "@angular/common/http";
import { Conexion } from "../../config/Conexion";
import { Entrada } from "../../modelos/entrada/Entrada";
import { Observable } from "rxjs";
import { Injectable, signal } from "@angular/core";
import { SessionRespuesta } from "../../modelos/respuesta/sesion-respuesta";

@Injectable({
    providedIn: 'root'
})

export class AnalisisService{
    private constApi = new Conexion;

    constructor(private http:HttpClient){}

    public analizar(entrada:Entrada): Observable<SessionRespuesta>{
        return this.http.post<SessionRespuesta>(
            `${this.constApi.getConexionUrl()}ejecutar-sesion/iniciar`, 
            entrada
        );
    }
}