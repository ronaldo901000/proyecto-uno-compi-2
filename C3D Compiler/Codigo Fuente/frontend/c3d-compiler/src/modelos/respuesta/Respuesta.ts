import { ErrorAnalisis } from "../error-analisis/ErrorAnalisis";

export interface Respuesta {
    hayErrores: boolean;
    errores: ErrorAnalisis[];
    codigoCGenerado:string;
}