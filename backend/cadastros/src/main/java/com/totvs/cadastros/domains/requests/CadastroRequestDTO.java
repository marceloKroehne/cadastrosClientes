package com.totvs.cadastros.domains.requests;

import com.totvs.cadastros.domains.Endereco;

import java.util.List;

public record CadastroRequestDTO(UsuarioRequestDTO usuario, List<String> telefones, List<EnderecoRequestDTO> enderecos) {
}
