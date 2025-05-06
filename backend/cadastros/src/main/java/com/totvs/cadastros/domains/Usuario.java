package com.totvs.cadastros.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="usuarios")
@Entity
public class Usuario {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String nome;
    @Column(unique = true)
    private String cpf;

}
