import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { ClienteService } from '../services/cliente.service';
import { Resposta } from '../models/resposta.model';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,RouterModule],
  templateUrl: './cadastrar-cliente.component.html'
})

export class CadastrarClienteComponent {
  clienteForm: FormGroup;

  constructor(private fb: FormBuilder,  private clienteService: ClienteService) {
    this.clienteForm = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
      enderecos: this.fb.array([
        this.criarEndereco()
      ]),
      telefones: this.fb.array([
        this.criarTelefone()
      ])
    });
  }

  criarEndereco(): FormGroup {
    return this.fb.group({
      logradouro: ['', Validators.required],
      numero: ['', Validators.required],
      complemento: ['', Validators.required],
      bairro: ['', Validators.required],
      cidade: ['', Validators.required],
      uf: ['', Validators.required],
      cep: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]]
    });
  }

  criarTelefone(): FormControl {
    return this.fb.control('', [Validators.required, Validators.pattern(/^\d{11}$/)]);
  }

  get enderecos() {
    return this.clienteForm.get('enderecos') as FormArray;
  }

  get telefones() {
    return this.clienteForm.get('telefones') as FormArray;
  }
  
  adicionarTelefone() {
    this.telefones.push(this.criarTelefone());
  }
  
  removerTelefone(index: number) {
    this.telefones.removeAt(index);
  }

  adicionarEndereco() {
    this.enderecos.push(this.criarEndereco());
  }

  removerEndereco(index: number) {
    this.enderecos.removeAt(index);
  }

  onSubmit() {
    this.clienteForm.markAllAsTouched();
    const invalidControl = this.findInvalidControl();

    if (invalidControl == null) {
      this.clienteService.cadastrar(this.clienteForm.value).subscribe({
        next: (response) => {
          const resposta: Resposta = response as Resposta;

          if(resposta.houveErro){
            alert(resposta.mensagem);
            return;
          }

          alert(resposta.mensagem);

          this.clienteForm.reset();
          this.enderecos.clear();
          this.telefones.clear();
          this.adicionarEndereco();
          this.adicionarTelefone();
        },

      });
    } else {
      
      if (invalidControl) {
        invalidControl.focus();
      }
    }
  }
  
  private findInvalidControl() {
    const controls = Object.keys(this.clienteForm.controls);
    for (let i = 0; i < controls.length; i++) {
      const control = this.clienteForm.get(controls[i]);
      if (control?.invalid && control?.touched) {
        const element = document.querySelector(`[formControlName=${controls[i]}]`) as HTMLElement;
        return element;
      }
    }
    return null;
  }
  
}

