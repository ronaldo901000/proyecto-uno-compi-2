import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class IndentacionService {
    private readonly ANCHO_TAB = 4;

    normalizar(texto: string): string {
        return texto
            .split('\n')
            .map(linea => {
                const match = linea.match(/^[\t \u00A0]*/);
                const indentacionOriginal = match ? match[0] : '';
                if (indentacionOriginal.length === 0) return linea;

                let columnas = 0;
                for (const c of indentacionOriginal) {
                    columnas += c === '\t' ? this.ANCHO_TAB : 1;
                }
                const indentacionCanonica = ' '.repeat(columnas);
                return indentacionCanonica + linea.substring(indentacionOriginal.length);
            })
            .join('\n');
    }

    recalcularCursor(original: string, normalizado: string, cursorOriginal: number): number {
        const lineasOriginal = original.substring(0, cursorOriginal).split('\n');
        const numeroLinea = lineasOriginal.length - 1;
        const columnaOriginal = lineasOriginal[lineasOriginal.length - 1].length;

        const lineasNormalizadasHastaAqui = normalizado.split('\n').slice(0, numeroLinea);
        const lineaActualNormalizada = normalizado.split('\n')[numeroLinea] ?? '';

        const lineaActualOriginal = original.split('\n')[numeroLinea] ?? '';
        const matchOriginal = lineaActualOriginal.match(/^[\t \u00A0]*/);
        const indentOriginalLen = matchOriginal ? matchOriginal[0].length : 0;

        let columnaAjustada: number;
        if (columnaOriginal <= indentOriginalLen) {
            const matchNuevo = lineaActualNormalizada.match(/^[\t \u00A0]*/);
            columnaAjustada = matchNuevo ? matchNuevo[0].length : 0;
        } else {
            const matchNuevo = lineaActualNormalizada.match(/^[\t \u00A0]*/);
            const nuevoIndentLen = matchNuevo ? matchNuevo[0].length : 0;
            columnaAjustada = columnaOriginal - indentOriginalLen + nuevoIndentLen;
        }

        const base = lineasNormalizadasHastaAqui.reduce((acc, l) => acc + l.length + 1, 0);
        return base + columnaAjustada;
    }
}