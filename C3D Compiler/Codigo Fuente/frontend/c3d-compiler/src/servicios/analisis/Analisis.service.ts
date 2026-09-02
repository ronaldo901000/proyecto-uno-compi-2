import { HttpClient } from "@angular/common/http";
import { Conexion } from "../../config/Conexion";
import { Entrada } from "../../modelos/entrada/Entrada";
import { Respuesta } from "../../modelos/respuesta/Respuesta";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})

export class AnalisisService{
    private constApi = new Conexion;

    constructor(private http:HttpClient){}

    public analizar(entrada:Entrada): Observable<Respuesta>{
        return this.http.post<Respuesta>(
            `${this.constApi.getConexionUrl()}analisis`, 
            entrada
        );
    }
}