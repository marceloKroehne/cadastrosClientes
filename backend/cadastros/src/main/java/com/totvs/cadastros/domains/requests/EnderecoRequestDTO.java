package com.totvs.cadastros.domains.requests;

public record EnderecoRequestDTO(String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) {
}
