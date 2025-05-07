import { Routes } from '@angular/router';
import { CadastrarClienteComponent } from './components/cadastrar-cliente.component';
import { ListarClientesComponent } from './components/listar-clientes.component';
import { EditarClienteComponent } from './components/editar-cliente.component';


export const routes: Routes = [
  { path: 'cadastrar-cliente', component: CadastrarClienteComponent },
  { path: 'listar-clientes', component: ListarClientesComponent },
  { path: 'editar-cliente/:id', component: EditarClienteComponent },
  { path: '', redirectTo: 'cadastrar-cliente', pathMatch: 'full' },
];
