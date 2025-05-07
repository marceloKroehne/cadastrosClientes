import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { Cliente } from '../models/cliente.model';
import { Observable } from 'rxjs';
import { Resposta } from '../models/resposta.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private apiUrl = 'http://127.0.0.1:8080/cadastro/'; 

  constructor(private http: HttpClient) {}

  cadastrar(cliente: Cliente): Observable<Resposta> {
    return this.http.post<Resposta>(this.apiUrl+"novoUsuario", cliente);
  }

  listarClientes(filtro: string): Observable<Resposta> {
    const params = new HttpParams().set('filtro', filtro);
  
    return this.http.get<Resposta>(this.apiUrl + "listarUsuarios", { params });
  }

  deletar(id : string): Observable<Resposta> {
    return this.http.delete<Resposta>(this.apiUrl + 'deletarUsuario/' + id);
  }

  editar(id: string, usuarioAtualizado: Cliente): Observable<Resposta> {
    return this.http.put<Resposta>(this.apiUrl + 'editarUsuario/' + id, usuarioAtualizado);
  }

  buscarPorId(id: string): Observable<Resposta> {
    const params = new HttpParams().set('id', id);
  
    return this.http.get<Resposta>(this.apiUrl + "buscarPorId", { params });
  }

}
