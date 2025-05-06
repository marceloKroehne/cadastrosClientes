package com.totvs.cadastros.domains.requests;

import com.totvs.cadastros.domains.Endereco;
import com.totvs.cadastros.domains.Telefone;
import com.totvs.cadastros.domains.Usuario;
import java.util.List;

public record BuscarUsuarioRequestDTO(Usuario usuario, List<Telefone> telefones, List<Endereco> enderecos) {
}
