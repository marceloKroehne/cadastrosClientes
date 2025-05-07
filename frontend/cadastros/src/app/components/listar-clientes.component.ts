import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../services/cliente.service';
import { Router } from '@angular/router';
import { Resposta } from '../models/resposta.model'; 
import { FormsModule } from '@angular/forms'; 
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-listar-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule], 
  templateUrl: './listar-clientes.component.html',
  styleUrls: ['../styles/listar-clientes.style.scss']
})
export class ListarClientesComponent implements OnInit {
  clientes: any[] = [];
  carregando: boolean = true;
  filtro: string = '';

  constructor(private clienteService: ClienteService, private router: Router) {}

  ngOnInit() {
    this.listarClientes();
  }

  listarClientes() {
    this.clienteService.listarClientes(this.filtro).subscribe({
      next: (resposta: Resposta) => {
        if (resposta.houveErro) {
          console.error('Erro na resposta:', resposta.mensagem);
        } else {
          this.clientes = resposta.dados as any[];
        }
        this.carregando = false;
      },
      error: (error) => {
        console.error('Erro ao listar clientes:', error);
        this.carregando = false;
      }
    });
  }

  aplicarFiltro() {
    this.carregando = true;
    this.listarClientes();
  }

  deletarCliente(id: string) {
    if (confirm('Tem certeza que deseja deletar este cliente?')) {
      this.clienteService.deletar(id).subscribe({
        next: (resposta: Resposta) => {
          if (resposta.houveErro) {
            console.error('Erro na resposta:', resposta.mensagem);
          } else {
            this.clientes = resposta.dados as any[];
          }
          this.carregando = false;
        },
        error: (error) => {
          console.error('Erro ao listar clientes:', error);
          this.carregando = false;
        }
      });
    }
  }
}
