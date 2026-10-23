import { Component } from '@angular/core';
import { ExploradorComponent } from "../../componentes/explorador/explorador.component";
import { EditorComponent } from "../../componentes/editor/editor.component";
import { TablaErroresComponent } from "../../componentes/tabla-errores/tabla-errores.component";
import { AvisoService } from '../../servicios/avisos/aviso.service';
import { ConsolaComponent } from '../../componentes/consola/consola.component';

@Component({
    selector: 'app-pagina-principal',
    imports: [ExploradorComponent, EditorComponent, TablaErroresComponent, ConsolaComponent],
    templateUrl: './pagina-principal.component.html',
    styleUrl: './pagina-principal.component.css'
})
export class PaginaPrincipalComponent {
    constructor(protected avisoService:AvisoService){}
}
