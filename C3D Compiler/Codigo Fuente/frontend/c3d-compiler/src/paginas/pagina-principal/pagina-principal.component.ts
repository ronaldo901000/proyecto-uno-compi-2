import { Component } from '@angular/core';
import { ExploradorComponent } from "../../componentes/explorador/explorador.component";
import { EditorComponent } from "../../componentes/editor/editor.component";
import { TablaErroresComponent } from "../../componentes/tabla-errores/tabla-errores.component";
import { AvisoService } from '../../servicios/avisos/aviso.service';

@Component({
    selector: 'app-pagina-principal',
    imports: [ExploradorComponent, EditorComponent, TablaErroresComponent],
    templateUrl: './pagina-principal.component.html',
    styleUrl: './pagina-principal.component.css'
})
export class PaginaPrincipalComponent {
    constructor(protected avisoService:AvisoService){}
}
