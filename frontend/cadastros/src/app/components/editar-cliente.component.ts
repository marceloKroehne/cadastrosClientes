import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteService } from '../services/cliente.service';  
import { Resposta } from '../models/resposta.model';  
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-editar-cliente',
  templateUrl: './editar-cliente.component.html',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,RouterModule]
})
export class EditarClienteComponent implements OnInit {
  clienteForm!: FormGroup;
  idCliente!: string;
  cliente : any;
  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.idCliente = this.route.snapshot.paramMap.get('id')!;

    this.clienteForm = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', [Validators.required, Validators.pattern('\\d{11}')]],
      enderecos: this.fb.array([]),
      telefones: this.fb.array([]),
    });

    this.clienteService.buscarPorId(this.idCliente).subscribe({ 
        next: (resposta: Resposta) => {
            if (resposta.houveErro) {
                console.error('Erro na resposta:', resposta.mensagem);
            } else {
                this.cliente  = resposta.dados as any;
                
                this.clienteForm.patchValue({
                    nome: this.cliente.usuario.nome,
                    cpf: this.cliente.usuario.cpf,
                  });
            

                this.cliente.enderecos.forEach((endereco: any) => {
                    this.enderecos.push(this.fb.group({
                      logradouro: [endereco.logradouro, Validators.required],
                      numero: [endereco.numero, Validators.required],
                      complemento: [endereco.complemento],
                      bairro: [endereco.bairro, Validators.required],
                      cidade: [endereco.cidade, Validators.required],
                      uf: [endereco.uf, Validators.required],
                      cep: [endereco.cep, [Validators.required, Validators.pattern('\\d{8}')]],
                    }));
                  });
            
                this.cliente.telefones.forEach((telefone: any) => {
                    this.telefones.push(this.fb.control(telefone.telefone, [Validators.required, Validators.pattern('\\d{10,11}')]));
                  });
            }

        },
        error: (error) => {
            console.error('Erro ao buscar cliente:', error);
        }
    });
  }

  get enderecos(): FormArray {
    return this.clienteForm.get('enderecos') as FormArray;
  }

  get telefones(): FormArray {
    return this.clienteForm.get('telefones') as FormArray;
  }

  adicionarEndereco() {
    this.enderecos.push(this.fb.group({
      logradouro: ['', Validators.required],
      numero: ['', Validators.required],
      complemento: [''],
      bairro: ['', Validators.required],
      cidade: ['', Validators.required],
      uf: ['', Validators.required],
      cep: ['', [Validators.required, Validators.pattern('\\d{8}')]],
    }));
  }

  removerEndereco(index: number) {
    this.enderecos.removeAt(index);
  }

  adicionarTelefone() {
    this.telefones.push(this.fb.control('', [Validators.required, Validators.pattern('\\d{10,11}')]));
  }

  removerTelefone(index: number) {
    this.telefones.removeAt(index);
  }

  onSubmit() {
    if (this.clienteForm.valid) {
      this.clienteService.editar(this.idCliente, this.clienteForm.value).subscribe(res => {
        alert('Cliente atualizado com sucesso!');
        this.router.navigate(['/listar-clientes']);
      });
    } else {
      this.clienteForm.markAllAsTouched();
    }
  }
}
