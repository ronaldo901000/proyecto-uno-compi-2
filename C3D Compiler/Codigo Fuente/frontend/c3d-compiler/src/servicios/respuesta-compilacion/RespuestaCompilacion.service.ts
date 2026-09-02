import { BehaviorSubject } from "rxjs";
import { Respuesta } from "../../modelos/respuesta/Respuesta";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})

export class RespuestaCompilacionService {

    private respuestaSubject = new BehaviorSubject<Respuesta | null>(null);
    respuesta$ = this.respuestaSubject.asObservable();

    public getRespuesta(): Respuesta | null {
        return this.respuestaSubject.value;
    }

    public setRespuesta(respuesta: Respuesta): void {
        this.respuestaSubject.next(respuesta);
    }
}