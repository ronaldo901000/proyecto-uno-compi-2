import { BehaviorSubject } from "rxjs";
import { Respuesta } from "../../modelos/respuesta/Respuesta";
import { Injectable } from "@angular/core";
import { SessionRespuesta } from "../../modelos/respuesta/sesion-respuesta";

@Injectable({
    providedIn: 'root'
})

export class RespuestaCompilacionService {

    private respuestaSubject = new BehaviorSubject<SessionRespuesta | null>(null);
    respuesta$ = this.respuestaSubject.asObservable();

    public getRespuesta(): SessionRespuesta | null {
        return this.respuestaSubject.value;
    }

    public setRespuesta(respuesta: SessionRespuesta): void {
        this.respuestaSubject.next(respuesta);
    }
}