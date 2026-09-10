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

    construirHtmlColoreado(texto: string, tokens: ColorToken[]): string {
        if (!tokens || tokens.length === 0) return this.escaparHtml(texto);

        tokens.sort((a, b) => a.inicio - b.inicio);

        let html = '';
        let ultimoIndice = 0;

        for (const t of tokens) {
            if (t.inicio < ultimoIndice) continue;

            if (t.inicio > ultimoIndice) {
                html += this.escaparHtml(texto.substring(ultimoIndice, t.inicio));
            }

            const valorToken = texto.substring(t.inicio, t.fin + 1);
            html += `<span style="color: ${t.color}">${this.escaparHtml(valorToken)}</span>`;

            ultimoIndice = t.fin + 1;
        }

        if (ultimoIndice < texto.length) {
            html += this.escaparHtml(texto.substring(ultimoIndice));
        }

        return html + '&nbsp;';
    }

    escaparHtml(str: string): string {
        return str
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#039;');
    }
}