import { ErrorAnalisis } from "../error-analisis/ErrorAnalisis";

export interface SessionRespuesta {
    exito: boolean,
    sessionId: string,
    salida: string,
    errores: ErrorAnalisis[];
    codigoRiscV: string,
    terminado?: boolean;
}