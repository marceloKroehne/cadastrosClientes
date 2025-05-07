export interface Endereco {
    logradouro: string;
    numero: string;
    complemento: string;
    bairro: string;
    cidade: string;
    uf: string;
    cep: string;
  }

export interface Cliente {
    id? : string;
    nome: string;
    cpf: string;
    enderecos: Endereco[];
    telefones: string[];
}
