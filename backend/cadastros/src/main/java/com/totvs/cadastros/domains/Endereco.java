package com.totvs.cadastros.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="enderecos")
public class Endereco {
    @Id
    @GeneratedValue
    private UUID id;

    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String numero;
    private String complemento;
    private String cep;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
}
