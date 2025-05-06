package com.totvs.cadastros.domains.requests;

import java.util.UUID;

public record UpdateRequestDTO(UUID id, CadastroRequestDTO cadastro) {
}
