package com.totvs.cadastros.domains.requests;

import com.totvs.cadastros.domains.Endereco;

import java.util.List;

public record CadastroRequestDTO(String nome, String cpf, List<String> telefones, List<EnderecoRequestDTO> enderecos) {
}
