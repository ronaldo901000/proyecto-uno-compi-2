export class Conexion{

    public readonly CONEXION_URL = 'http://localhost:8080/cd3-compiler-api/api/v1/';

    public getConexionUrl():string{
        return this.CONEXION_URL;
    }
}