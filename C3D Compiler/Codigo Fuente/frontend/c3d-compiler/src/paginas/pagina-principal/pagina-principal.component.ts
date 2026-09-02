import { Component } from '@angular/core';
import { ExploradorComponent } from "../../componentes/explorador/explorador.component";
import { EditorComponent } from "../../componentes/editor/editor.component";
import { TablaErroresComponent } from "../../componentes/tabla-errores/tabla-errores.component";

@Component({
  selector: 'app-pagina-principal',
  standalone: true,
  imports: [ExploradorComponent, EditorComponent, TablaErroresComponent],
  templateUrl: './pagina-principal.component.html',
  styleUrl: './pagina-principal.component.css'
})
export class PaginaPrincipalComponent {

}
