import { HttpClient } from "@angular/common/http";
import { Conexion } from "../../config/Conexion";
import { Injectable, signal } from "@angular/core";
import { Observable } from "rxjs";
import { EjecucionEnviar } from "../../modelos/respuesta/ejecucionEnviar";
import { SessionRespuesta } from "../../modelos/respuesta/sesion-respuesta";

@Injectable({ providedIn: 'root' })
export class EjecucionService {
    private constApi = new Conexion;

    public idSession = signal<string>('');
    public salida = signal<string>('');
    public ejecutando = signal<boolean>(false);

    constructor(private http: HttpClient) { }

    public enviar(ejecucion: EjecucionEnviar): Observable<SessionRespuesta> {
        return this.http.post<SessionRespuesta>(
            `${this.constApi.getConexionUrl()}ejecutar-sesion/enviar`, ejecucion);
    }

    public salidaPendiente(): Observable<SessionRespuesta> {
        return this.http.post<SessionRespuesta>(
            `${this.constApi.getConexionUrl()}ejecutar-sesion/salida`,
            { sessionId: this.idSession() });
    }

    public detener(): Observable<SessionRespuesta> {
        return this.http.post<SessionRespuesta>(
            `${this.constApi.getConexionUrl()}ejecutar-sesion/detener`,
            { sessionId: this.idSession() });
    }

    public reiniciar(): void {
        this.salida.set('');
        this.idSession.set('');
        this.ejecutando.set(false);
    }
}